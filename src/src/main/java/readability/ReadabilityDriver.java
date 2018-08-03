/******************************************************************************
 * Copyright (c) 2010 Basis Technology Corp.
 * 
 * Basis Technology Corp. licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package readability;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * At the moment this class will take all html files from the flat directory: ./src/test/resources/htmlInput/
 * and write them to: ./src/test/resources/textOutput/
 * 
 * In the future, a nice thing to do might be to abstract this so that it can process just about anything you
 * throw in there.  It's a matter of using the appropriate PageReaders.  Also, having the directories be hard-coded
 * might be a problem in the future.
 */

public  class ReadabilityDriver {
    
    //the logger
    private static final Logger LOG = LoggerFactory.getLogger(ReadabilityDriver.class);
    
    //the paths
    private static final String INPUT_PATH = "/prepage/";
    private static final String OUTPUT_PATH = "/page/";
    
    //private constructor
    public ReadabilityDriver() { }
    
    public  void readabilityDriver(String path,String sessionid) throws IOException {
        String pathprepage=System.getProperty("user.dir")+"/"+sessionid+INPUT_PATH+path+".html";
        String pathpage=System.getProperty("user.dir")+"/"+sessionid+OUTPUT_PATH+path+".txt";
        //input directory file
        File inputDir = new File(System.getProperty("user.dir")+"/"+sessionid+INPUT_PATH);
        
        //create the FilePageReader for Readability
        FilePageReader reader = new FilePageReader();
        reader.setBaseDirectory(inputDir);
        
        //instantiate Readability and set reader
        Readability readability = new Readability();
        readability.setPageReader(reader);
        readability.setReadAllPages(false);
        reader.setCharsetDetector(new TikaCharsetDetector());

            try {
                LOG.info("processing page: " + pathprepage);
                readability.processDocument(pathprepage);
            } catch (PageReadException e) {
                LOG.error("PageReadError while processing: " + pathprepage);
                e.printStackTrace();
                
            }
            
            //write the output, forcing a sentence break between title and body with \u2029.
            String title = readability.getTitle().trim() + "\u2029";
            String keyWords = readability.getKeyWords();
            String content = readability.getArticleText();
            //int lastSlash = path.replace("\\", "/").lastIndexOf('/');
            //String returnText = System.getProperty("user.dir")+OUTPUT_PATH + path.replaceAll("html$", "txt");
            FileOutputStream fos = new FileOutputStream(pathpage);
            fos.write((title + System.getProperty("line.separator") +keyWords+System.getProperty("line.separator")+ content).getBytes("UTF8"));
            fos.flush();
            fos.close();
        }
    }

