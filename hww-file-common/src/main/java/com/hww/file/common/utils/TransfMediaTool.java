package com.hww.file.common.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hww.base.util.DateUtils;
import com.hww.file.common.vo.FileImgVo;
import com.hww.file.common.vo.FileInfoVo;
import com.hww.file.common.vo.FileVideoVo;

/**
 * 注意:
 * 保存截图文件目录必须存在
 * 视频保存路径不能有空格
 * @author hewei
 *
 */
public class TransfMediaTool {
	
	private static Log log = LogFactory.getLog(TransfMediaTool.class);

	/**
	 * 
	 * 获取图片的第一帧 ffmpeg commandLine： ffmpeg -y -i /usr/local/bin/lh.3gp -vframes 1 -r
	 * 1 -ac 1 -ab 2 -s 320x240 -f image2 /usr/local/bin/lh.jpg
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param picType
	 *            要保存的图片格式：jpg,jpeg,gif
	 * @throws IOException
	 * @throws IOException
	 */

	public FileVideoVo captureFirstFrame(File sourceFile,String ffmpegPath,String coverPath, String picType) {
		String fileName = sourceFile.getName();
		String prefix = fileName.substring(0, fileName.lastIndexOf("."));
		String surffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (!isSurpportedType(surffix)) {
			throw new RuntimeException("unsurpported file type " + surffix);
		}
		if(StringUtils.isEmpty(picType)) {
			picType=".jpg";
		}
		List<String> cutpic = new ArrayList<String>();  
        cutpic.add(ffmpegPath);  
        cutpic.add("-i");  
        cutpic.add(sourceFile.getAbsolutePath()); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）  
        cutpic.add("-y");  
        cutpic.add("-f");  
        cutpic.add("image2");  
        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间  
        cutpic.add("0"); // 添加起始时间为第17秒  
        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间  
        cutpic.add("0.001"); // 添加持续时间为1毫秒  
        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小  
        cutpic.add("500*400"); // 添加截取的图片大小为350*240 
		Date date = new Date();
		String dateStr = DateUtils.getDateStr(date);
		String destination = coverPath+dateStr+File.separator+prefix+picType;
		cutpic.add(destination);// 添加截取的图片的保存路径  

		FileVideoVo result = execCmd(cutpic);
//		if(result!=null) {
//			log.info("视频时长:"+result.getLength());
//			log.info("视频码率:"+result.getDataRate());
//			//设置封面
//			FileImgVo cover = new FileImgVo();
//			
//			FileInfoVo coverInfoVo = new FileInfoVo();
//			coverInfoVo.setFileName(fileName);
//			coverInfoVo.setFileRelativePath(coverPath+dateStr);
//			coverInfoVo.setFileSaveName(prefix);
//			coverInfoVo.setFileExtensionName(picType);
//			
//			File file = new File(destination);
//			FileInputStream fis;
//			try {
//				fis = new FileInputStream(file);
//				BufferedImage bufferedImage = ImageIO.read(fis);
//				if (bufferedImage!=null) {
//					cover.setImgFileHight(bufferedImage.getHeight());
//					cover.setImgFileWidth(bufferedImage.getWidth());
//				}
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			cover.setFileInfo(coverInfoVo);
//			result.setCover(cover);
//		}
		
		return result;
	}

	/**
	 * 视频转码flv
	 * 
	 * @param ffmpegPath
	 *            转码工具的存放路径
	 * @param upFilePath
	 *            用于指定要转换格式的文件,要截图的视频源文件
	 * @param codcFilePath
	 *            格式转换后的的文件保存路径
	 * @return
	 * @throws Exception
	 */
	public void processFLV(String ffmpegPath, String upFilePath, String codcFilePath) {
		// 创建一个List集合来保存转换视频文件为flv格式的命令
		List<String> convert = new ArrayList<String>();
		convert.add(ffmpegPath); // 添加转换工具路径
		convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
		convert.add(upFilePath); // 添加要转换格式的视频文件的路径
		convert.add("-ab");
		convert.add("56");
		convert.add("-ar");
		convert.add("22050");
		convert.add("-q:a");
		convert.add("8");
		convert.add("-r");
		convert.add("15");
		convert.add("-s");
		convert.add("600*500");

		/*
		 * convert.add("-qscale"); // 指定转换的质量 convert.add("6"); convert.add("-ab"); //
		 * 设置音频码率 convert.add("64"); convert.add("-ac"); // 设置声道数 convert.add("2");
		 * convert.add("-ar"); // 设置声音的采样频率 convert.add("22050"); convert.add("-r"); //
		 * 设置帧频 convert.add("24"); convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
		 */
		convert.add(codcFilePath);

		try {
			Process videoProcess = new ProcessBuilder(convert).redirectErrorStream(true).start();
			new PrintStream(videoProcess.getInputStream()).start();
			videoProcess.waitFor();

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 先用mencoder转换为avi(ffmpeg能解析的)格式
	 * 
	 * @param mencoderPath
	 *            转码工具的存放路径
	 * @param upFilePath
	 *            用于指定要转换格式的文件,要截图的视频源文件
	 * @param codcFilePath
	 *            格式转换后的的文件保存路径
	 * @return
	 * @throws Exception
	 */
	public String processAVI(String mencoderPath, String upFilePath, String codcAviPath) {
		boolean flag = false;
		List<String> commend = new ArrayList<String>();
		commend.add(mencoderPath);
		commend.add(upFilePath);
		commend.add("-oac");
		commend.add("mp3lame");
		commend.add("-lameopts");
		commend.add("preset=64");
		commend.add("-lavcopts");
		commend.add("acodec=mp3:abitrate=64");
		commend.add("-ovc");
		commend.add("xvid");
		commend.add("-xvidencopts");
		commend.add("bitrate=600");
		commend.add("-of");
		commend.add("avi");
		commend.add("-o");
		commend.add(codcAviPath);
		try {
			// 预处理进程
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.redirectErrorStream(true);

			// 进程信息输出到控制台
			Process p = builder.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				log.info(line);
			}
			p.waitFor();// 直到上面的命令执行完，才向下执行
			return codcAviPath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	private FileVideoVo execCmd(List<String> cmd) {
		FileVideoVo fileVideoVo = null;
        final ProcessBuilder pb = new ProcessBuilder();
        try {  
        	pb.redirectErrorStream(true);  
        	pb.command(cmd);
            final Process p = pb.start();  
            InputStream in = p.getInputStream();  
            fileVideoVo = pattInfo(in);  
            // 开启单独的线程来处理输入和输出流，避免缓冲区满导致线程阻塞.  
            WatchThread wt = new WatchThread(p);  
            wt.start();  
            p.waitFor();
            ArrayList<String> commandStream = wt.getStream();  
            wt.setOver(true); 
            
            p.getErrorStream().close();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
            Thread.currentThread().interrupt();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
        return fileVideoVo;
    }  
	
    class WatchThread extends Thread   {   
        Process   p;   
        boolean   over;  
        ArrayList<String> stream;  
        public WatchThread(Process p) {   
            this.p = p;   
            over = false;  
            stream = new ArrayList<String>();  
        }   
        public void run() {   
          try {   
               if(p == null)return;   
               Scanner br = new Scanner(p.getInputStream());  
               while (true) {   
                   if (p==null || over) break;   
                   while(br.hasNextLine()){  
                       String tempStream = br.nextLine();  
                       if(tempStream.trim()==null||tempStream.trim().equals(""))continue;  
                       stream.add(tempStream);  
                   }  
               }   
               } catch(Exception   e){e.printStackTrace();}   
        }  
          
        public void setOver(boolean   over)   {   
              this.over   =   over;   
        }  
        public ArrayList<String> getStream() {  
            return stream;  
        }  
    }   
	
	// 负责从返回的内容中解析  
    /** 
     * Input #0, avi, from 'c:\join.avi': Duration: 00:00:10.68(时长), start: 
     * 0.000000(开始时间), bitrate: 166 kb/s(码率) Stream #0:0: Video: msrle 
     * ([1][0][0][0] / 0x0001)(编码格式), pal8(视频格式), 165x97(分辨率), 33.33 tbr, 33.33 
     * tbn, 33.33 tbc Metadata: title : AVI6700.tmp.avi Video #1 
     */  
    public FileVideoVo pattInfo(InputStream is) {
    	FileVideoVo fileVideoVo = new FileVideoVo();
        String info = read(is);
        log.info(info);
        String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
        Pattern pattern = Pattern.compile(regexDuration);  
        Matcher m = pattern.matcher(info);  
        if (m.find()) {  
        	fileVideoVo.setLength(getTimelen(m.group(1))); //视频时长
        	//fileVideoVo.setBeginTime(m.group(2));
        	try {
        		log.debug(m.group(3));
        		fileVideoVo.setDataRate(Integer.parseInt(m.group(3))); //视频码率
        		//fileVideoVo.setResolution(m.group(6)); //视频分辨率
        	}catch(Exception e) {
        		log.info(e.getMessage());
        	}
        }  
        return fileVideoVo;  
    }  
    // 负责从返回信息中读取内容  
    private String read(InputStream is) {  
        BufferedReader br = null;  
        StringBuffer sb = new StringBuffer();  
        try {  
            br = new BufferedReader(new InputStreamReader(is), 500);  
    
            String line = "";  
            while ((line = br.readLine()) != null) {  
                // System.out.println(line);  
                sb.append(line);  
            }  
            br.close();  
        } catch (Exception e) {  
        } finally {  
            try {  
                if (br != null)  
                    br.close();  
            } catch (Exception e) {  
            }  
        }  
        return sb.toString();  
    }  
    //格式:"00:00:10.68"  
    private int getTimelen(String timelen){  
        int min=0;  
        String strs[] = timelen.split(":");  
        if (strs[0].compareTo("0") > 0) {  
            min+=Integer.valueOf(strs[0])*60*60;//秒  
        }  
        if(strs[1].compareTo("0")>0){  
            min+=Integer.valueOf(strs[1])*60;  
        }  
        if(strs[2].compareTo("0")>0){  
            min+=Math.round(Float.valueOf(strs[2]));  
        }  
        return min;  
    }  

	class PrintStream extends Thread {
		java.io.InputStream __is = null;

		public PrintStream(java.io.InputStream is) {
			__is = is;
		}

		public void run() {
			try {
				while (this != null) {
					int _ch = __is.read();
					if (_ch != -1)
						System.out.print((char) _ch);
					else
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean isSurpportedType(String type) {
		Pattern pattern = Pattern.compile("(asx|asf|mpg|wmv|3gp|mp4|mov|avi|flv){1}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(type);
		return matcher.find();
	}

}
