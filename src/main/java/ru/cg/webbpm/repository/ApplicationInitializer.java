package ru.cg.webbpm.repository;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import ru.cg.webbpm.repository.config.WebConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * @author m.popov
 */
public class ApplicationInitializer implements WebApplicationInitializer {

  private static final String DISPATCHER = "dispatcher";

  @Override
  public void onStartup(ServletContext servletContext) {
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.register(WebConfig.class);
    servletContext.addListener(new ContextLoaderListener(context));
    ServletRegistration.Dynamic servlet = servletContext.addServlet(DISPATCHER, new DispatcherServlet(context));
    servlet.addMapping("/");
    servlet.setLoadOnStartup(1);
  }
}
