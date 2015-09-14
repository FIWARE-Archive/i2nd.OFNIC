package uniroma1.fiware.ofnic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.WebApplicationInitializer;

import uniroma1.fiware.ofnic.data.Constants;
import uniroma1.fiware.ofnic.data.TempData;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

@ComponentScan
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class Ofnic implements WebApplicationInitializer /*implements CommandLineRunner*/ {


	/*@Autowired
	private SecureService service;*/

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.addFilter("corsFilter", SimpleCORSFilter.class);
        onStartup(servletContext);
	}

	@Bean
	public Filter getFilter(){
		return new SimpleCORSFilter();
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean () {
	    SimpleCORSFilter compressingFilter = new SimpleCORSFilter();
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    registrationBean.setFilter(compressingFilter);
	    return registrationBean;
	}

    public static void main(String[] args) {
    	setupAuthenticatedClient();
        SpringApplication.run(Ofnic.class, args);
        loadQosQueueConfiguration();
        //setupAuthenticatedClient();
    }



	/*@Override
	public void run(String... args) throws Exception {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("", "", AuthorityUtils
						.commaSeparatedStringToAuthorityList("ROLE_USER")));
		try {
			System.out.println(this.service.secure());
		}
		finally {
			SecurityContextHolder.clearContext();
		}
	}*/



	@Configuration
    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
        	String user = null, pwd = null;
        	try{
    			BufferedReader br = new BufferedReader(new FileReader(new File("").getAbsolutePath()+"/ofnic.conf"));
    			String line;
    			int i=0;
    			while ( (line = br.readLine()) != null) {
    				if(!line.equals("")){
    					if(line.split(":")[0].equals("user"))
    						user = line.split(":")[1];
    					if(line.split(":")[0].equals("pwd"))
    						pwd = line.split(":")[1];
    				}
    			}
    			br.close();
    			if(user==null || pwd==null)
    				throw new NullPointerException("Bad ofnic.conf configuration file");
    			auth.inMemoryAuthentication().withUser(user).password(pwd).roles("USER");
    			}
    		catch(Exception e){
    			TempData.logger.error(e.getMessage());
    			}
        }
    }



	private static void loadQosQueueConfiguration() {
		TempData.logger.info("Loading QoS queue configuration...");
		TempData.queueConfiguration = new ArrayList<Long>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File("").getAbsolutePath()+"/qos_queue.conf"));
			String line;
			TempData.queueConfiguration = new ArrayList<Long>();
			int queue = 0;
			while ( (line = br.readLine()) != null) {
				if(!line.equals("")){
					TempData.queueConfiguration.add(Long.parseLong(line));
					TempData.logger.info("Queue "+queue+":"+TempData.queueConfiguration.get(queue)+"kbps");
					queue++;
				}
			}
			br.close();
			}
		catch(Exception e){
			TempData.logger.error(e.getMessage());
			}
		}



	private static void setupAuthenticatedClient(){
		TempData.client = Client.create();
		TempData.client.addFilter(new HTTPBasicAuthFilter(Constants.ODL_username, Constants.ODL_password));

		WebResource webResource = TempData.client.resource(Constants.odlGetAllNodesWSPath);
    	ClientResponse response = webResource.get(ClientResponse.class);
    	TempData.logger.info("Getting cookies");
    	TempData.odlCookies = response.getCookies();

    	//TempData.client = Client.create();
	}



}

