package com.ibcsprimax.hrm.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class JasperUtils {
	
	 public static final Logger logger = Logger.getLogger(JasperUtils.class);
	    
	    public void jasperPrintWithList(List<?> list, Map<String, Object> paramsMap, String jasperFilePath,
				String downLoadFileName) throws Exception {

			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(jasperFilePath);

			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, paramsMap,
					new JRBeanCollectionDataSource(list));

			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

			

			HttpServletResponse httpServletResponse = ((ServletRequestAttributes) requestAttributes).getResponse();

			httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + downLoadFileName + ".pdf");

			try (ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream()) {

				JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

				logger.info("Download complete.");
				
				servletOutputStream.flush();
				servletOutputStream.close();

				return ;
			}   
			
			
			
		}
	    
	    
	    
	    
	    

}
