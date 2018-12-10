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

import com.infosisarg.PluginManagerHolder;
import com.infosisarg.api.PluginWebInterface;

@ManagedBean(name = "pluginsMBean")
@SessionScoped
public class PluginsMBean implements Serializable {
    private static final long serialVersionUID = 7473027855852017369L;

    public List<PluginWrapper> getPlugins() {
        return PluginManagerHolder.getPluginManager().getPlugins();
    }
    
    public Boolean isWeb(String pluginId) {
    	if(pluginId!=null && !pluginId.isEmpty()) {
    		List extensions = PluginManagerHolder.getPluginManager().getExtensions(pluginId);
    		if(extensions != null && !extensions.isEmpty()) {
		    	if(PluginManagerHolder.getPluginManager().getExtensions(pluginId).get(0) instanceof PluginWebInterface) {
					return true;
				}
	    	}
    	}
    	return false;
    }
    
    public List<PluginWebInterface> getPluginsWeb() {
        return PluginManagerHolder.getPluginManager().getExtensions(PluginWebInterface.class);
    }
    
    public boolean isActive(PluginWrapper pluginWrapper) {
        return pluginWrapper.getPluginState().equals(PluginState.STARTED);

    }

    public String disable(PluginWrapper pluginWrapper) {
        PluginManagerHolder.getPluginManager().stopPlugin(pluginWrapper.getPluginId());
        return "";
    }

    public String delete(PluginWrapper pluginWrapper) {
        PluginManagerHolder.getPluginManager().deletePlugin(pluginWrapper.getPluginId());
        return "";
    }

    public String enable(PluginWrapper pluginWrapper) {
        PluginManagerHolder.getPluginManager().enablePlugin(pluginWrapper.getPluginId());
        PluginManagerHolder.getPluginManager().startPlugin(pluginWrapper.getPluginId());
        PluginManagerHolder.initPlugin(pluginWrapper.getPluginId());
        return "";
    }

    public void reload() {
    	PluginManagerHolder.getPluginManager().getPlugins().forEach(p -> PluginManagerHolder.getPluginManager().unloadPlugin(p.getPluginId()));
    	PluginManagerHolder.getPluginManager().loadPlugins();
    	PluginManagerHolder.getPluginManager().getPlugins().forEach(p -> {
    		PluginManagerHolder.getPluginManager().enablePlugin(p.getPluginId());
    		PluginManagerHolder.getPluginManager().startPlugin(p.getPluginId());
    		PluginManagerHolder.initPlugin(p.getPluginId());
		});
    	
    }
    
    private UploadedFile uploadedFile;

    public void submit() throws IOException {
        String fileName = FilenameUtils.getName(uploadedFile.getName());
        String pluginsHome = PluginManagerHolder.getPluginManager().getPluginsRoot().toString();
        byte[] bytes = uploadedFile.getBytes();
        FileOutputStream out = new FileOutputStream(pluginsHome + "/" + fileName);
        out.write(bytes);
        out.close();
        String newPluginID = PluginManagerHolder.getPluginManager().loadPlugin(new File(pluginsHome + "/" + fileName).toPath());
        PluginManagerHolder.getPluginManager().enablePlugin(newPluginID);
        PluginManagerHolder.getPluginManager().startPlugin(newPluginID);
        PluginManagerHolder.initPlugin(newPluginID);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(String.format("Plugin '%s'  successfully installed!", fileName)));
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

}
