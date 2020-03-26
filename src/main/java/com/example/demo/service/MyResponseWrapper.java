package com.example.demo.service;

import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class MyResponseWrapper extends HttpServletResponseWrapper {

    private Logger logger = Logger.getLogger(MyResponseWrapper.class);

//    MyServletOutputStream stream = new MyServletOutputStream();

    public MyResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public ServletOutputStream getOutputStream() throws IOException {
//        logger.info(stream);
        return super.getOutputStream();
    }

//    public PrintWriter getWriter() throws IOException {
//        return new PrintWriter(stream);
//    }
}


//class MyServletOutputStream extends ServletOutputStream {
//    private ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//    public void write(int b) throws IOException {
//        out.write(b);
//    }
//
//}
