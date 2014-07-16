/**
 * 
 */
package com.automation.seletest.core.selenium.configuration.chrome;

import java.io.File;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
@Scope("prototype")
public class ChromeOptions {

	public ChromeOptions(
			List<String> argumentsChrome,
			List<File> extensionChrome,
			File binaryChrome,
			String binaryPathChrome){

	}

	@Getter @Setter  private List<String> arguments;
	@Getter @Setter  private List<File> extensions;
	@Getter @Setter  private File binary;
	@Getter @Setter  private String binaryPath;


	static class BuildChromeOptions{
		
		@Getter @Setter  private List<String> arguments;
		@Getter @Setter  private List<File> extensions;
		@Getter @Setter  private File binary;
		@Getter @Setter  private String binaryPath;
		
		public BuildChromeOptions withArguments(List<String> argumentsChrome){
			this.arguments=argumentsChrome;
			return this;
		}
		
		public BuildChromeOptions withExtensions(List<File> extensionChrome){
			this.extensions=extensionChrome;
			return this;
		}
		
		public BuildChromeOptions withBinary(File binaryChrome){
			this.binary=binaryChrome;
			return this;
		}
		
		public BuildChromeOptions withBinaryPath(String binaryPathChrome){
			this.binaryPath=binaryPathChrome;
			return this;
		}
		
		public build ChromeOptions(){
			
		}
		
		
	}
}
