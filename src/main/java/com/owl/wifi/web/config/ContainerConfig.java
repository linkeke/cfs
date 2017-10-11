package com.owl.wifi.web.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

@Component
public class ContainerConfig implements EmbeddedServletContainerCustomizer  {  
    /** 
     * @param container 
     * @see org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer#customize(org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer) 
     */  
    @Override  
    public void customize(ConfigurableEmbeddedServletContainer container) {  
         container.setContextPath("/owl_wifi");  
         container.setSessionTimeout(1800);  
    }  
       
}  