package com.automation.setest.groovy.configuration

import groovy.util.logging.*
/**
 * WebDriver various groovy functions
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
class WebDriverOptions {

    /**
     * Download ChromeDriver or IEDriver or PhantomJS  executables in local environment if not exists using AntBuilder
     * @param file
     * @param path
     */
    public static synchronized void downloadDriver(File file, String path) {
        if (!file.exists()) {
            log.info('Download {} from Central Repo', file)
            def ant = new AntBuilder()
            ant.get(src: path, dest: 'driver.zip')
            ant.unzip(src: 'driver.zip', dest: file.parent)
            ant.delete(file: 'driver.zip')
            ant.chmod(file: file, perm: '700')
        }
        else {
            log.info('{} already exists', file)
        }

    }
}
