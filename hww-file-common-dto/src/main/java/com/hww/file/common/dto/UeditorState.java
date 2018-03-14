package com.hww.file.common.dto;

import java.io.Serializable;

public class UeditorState implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UeditorState() {
		super();
	}
	public UeditorState(String state) {
		super();
		this.state = state;
	}
	public UeditorState(String state, String url) {
		super();
		this.state = state;
		this.url = url;
	}
	public UeditorState(String state, String url, String title, String original) {
		super();
		this.state = state;
		this.url = url;
		this.title = title;
		this.original = original;
	}
	private String state;
	private String url;
	private String title;
	private String original;
	public String getState() {
		return state;
	}
	public UeditorState setState(String state) {
		this.state = state;
		return this;
	}
	public String getUrl() {
		return url;
	}
	public UeditorState setUrl(String url) {
		this.url = url;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public UeditorState setTitle(String title) {
		this.title = title;
		return this;
	}
	public String getOriginal() {
		return original;
	}
	public UeditorState setOriginal(String original) {
		this.original = original;
		return this;
	}
	
//    @RequestMapping(value = {"/upload","/upload/"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody Object uploadAttachment(HttpServletRequest request,@RequestParam(name = "upfile", required = false) MultipartFile file, String module) {
//    	 String filePath="";
//    	 String fileName="";
//         try {
//        	 fileName = file.getOriginalFilename();
//        	 filePath= UpImageUtil.transferAttachment(file, JYGlobleConst.ATTACHMENT_ROOT_PATH);
//         }catch (Exception e) {
//        	 return new UeditorState("上传失败").setTitle("上传失败：【"+e.getMessage()+"】");
//         }
//         return new UeditorState( "SUCCESS", filePath ).setOriginal(fileName).setTitle("");
//    }
	

}
