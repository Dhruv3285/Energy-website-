package uk.ac.city.aczm039.Energy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uk.ac.city.aczm039.Energy.services.EnergyUserDetails;

import javax.sql.DataSource;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private DataSource dataSource;
    /**
     * Custom user details service that also records user login
     * history in the database.
     */
    private EnergyUserDetails energyUserDetails;

    /**
     * Constructor based dependency injection of custom user details service.
     *
     * @param energyUserDetails
     */
    @Autowired
    public SecurityConfig(EnergyUserDetails energyUserDetails,DataSource dataSource){
        this.energyUserDetails = energyUserDetails;
        this.dataSource = dataSource;
    }

    /**
     * Set the authentication service to use the custom user details service.
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT email, password, 'TRUE' as enabled FROM ENERGY_USER WHERE email=?")
                .authoritiesByUsernameQuery("SELECT email, 'ROLE_USER' as authority FROM ENERGY_USER WHERE email=?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }


    /**
     * Only provide access to the profile and booking pages to logged in users.
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception{

        http.authorizeRequests()
                .antMatchers("/enter", "/profile")
                .permitAll()
                .antMatchers("/enter", "/profile")
                .authenticated()
                .and()
                .formLogin();

        //disable security so that the database console can be accessed.
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    /**
     * Method to construct an authentication provided which uses the  custom user details
     * service so it can be supplied to the auth object (together with a BCrypt password
     * encoder. Method is called when configuring authentication.
     *
     * @return DaoAuthenticationProvider an authentication provider that contains the custom
     * user details service and password encoder.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(energyUserDetails);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }
}