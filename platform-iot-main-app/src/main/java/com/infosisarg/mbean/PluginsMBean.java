package com.infosisarg.mbean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosisarg.PluginManagerHolder;
import com.infosisarg.api.PluginWebInterface;

@ManagedBean(name = "pluginsMBean")
@SessionScoped
public class PluginsMBean implements Serializable {
    private static final long serialVersionUID = 7473027855852017369L;
    private static final Logger logger = LoggerFactory.getLogger(PluginsMBean.class);

    public List<PluginWrapper> getPlugins() {
        return PluginManagerHolder.getInstance().getPluginManager().getPlugins();
    }
    
    public Boolean isWeb(String pluginId) {
    	if(pluginId!=null && !pluginId.isEmpty()) {
    		List extensions = PluginManagerHolder.getInstance().getPluginManager().getExtensions(pluginId);
    		if(extensions != null && !extensions.isEmpty()) {
		    	if(PluginManagerHolder.getInstance().getPluginManager().getExtensions(pluginId).get(0) instanceof PluginWebInterface) {
					return true;
				}
	    	}
    	}
    	return false;
    }
    
    public List<PluginWebInterface> getPluginsWeb() {
        return PluginManagerHolder.getInstance().getPluginManager().getExtensions(PluginWebInterface.class);
    }
    
    public boolean isActive(PluginWrapper pluginWrapper) {
        return pluginWrapper.getPluginState().equals(PluginState.STARTED);

    }

    public String disable(PluginWrapper pluginWrapper) {
        PluginManagerHolder.getInstance().getPluginManager().stopPlugin(pluginWrapper.getPluginId());
        return "";
    }

    public String delete(PluginWrapper pluginWrapper) {
        PluginManagerHolder.getInstance().getPluginManager().deletePlugin(pluginWrapper.getPluginId());
        return "";
    }

    public String enable(PluginWrapper pluginWrapper) {
        PluginManagerHolder.getInstance().getPluginManager().enablePlugin(pluginWrapper.getPluginId());
        PluginManagerHolder.getInstance().getPluginManager().startPlugin(pluginWrapper.getPluginId());
        PluginManagerHolder.getInstance().initPlugin(pluginWrapper.getPluginId());
        return "";
    }

    public void reload() {
    	PluginManagerHolder.getInstance().getPluginManager().getPlugins().forEach(p -> PluginManagerHolder.getInstance().getPluginManager().unloadPlugin(p.getPluginId()));
    	PluginManagerHolder.getInstance().getPluginManager().loadPlugins();
    	PluginManagerHolder.getInstance().getPluginManager().getPlugins().forEach(p -> {
    		PluginManagerHolder.getInstance().getPluginManager().enablePlugin(p.getPluginId());
    		PluginManagerHolder.getInstance().getPluginManager().startPlugin(p.getPluginId());
    		PluginManagerHolder.getInstance().initPlugin(p.getPluginId());
		});
    	
    }
    
    private UploadedFile uploadedFile;

    public void submit() throws IOException {
        String fileName = FilenameUtils.getName(uploadedFile.getName());
        String pluginsHome = PluginManagerHolder.getInstance().getPluginManager().getPluginsRoot().toString();
        byte[] bytes = uploadedFile.getBytes();
        FileOutputStream out = new FileOutputStream(pluginsHome + "/" + fileName);
        logger.info("uploading file: " + fileName + " destination: " + pluginsHome + "/" + fileName);
        out.write(bytes);
        out.close();
        String newPluginID = PluginManagerHolder.getInstance().getPluginManager().loadPlugin(new File(pluginsHome + "/" + fileName).toPath());
        PluginManagerHolder.getInstance().getPluginManager().enablePlugin(newPluginID);
        PluginManagerHolder.getInstance().getPluginManager().startPlugin(newPluginID);
        PluginManagerHolder.getInstance().initPlugin(newPluginID);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(String.format("Plugin '%s'  successfully installed!", fileName)));
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

}
