package com.classic.core.consts;

/**
 * 常用的MIME扩展类型对应的ContentType
 *
 * @author 续写经典
 * @date 2015/11/4
 */
public class MIME {
	/** 二进制流,匹配所有类型 */
	public static final String FILE      = "application/octet-stream";

	/**
	 * "application/xml" 和 "text/xml" 二者功能一模一样，唯一的区别就是编码格式.<br/>
	 * text/xml : 忽略xml头所指定编码格式而默认采用us-ascii编码<br/>
	 * application/xml : 会根据xml头指定的编码格式来编码
	 */
	public static final String HTTP_XML  = "application/xml;charset=UTF-8";
	public static final String HTTP_JSON = "application/json;charset=UTF-8";

	/*********** 文件 **********/
	/** APK文件(安卓系统) */
	public static final String APK       = "application/vnd.android.package-archive";
	/** IPA文件(IPHONE) */
	public static final String IPA       = "application/vnd.iphone";
	/** XAP文件(Windows Phone 7) */
	public static final String XAP       = "application/x-silverlight-app";
	/** CAB文件(Windows Mobile) */
	public static final String CAB       = "application/vnd.cab-com-archive";
	/** SIS文件(symbian平台/S60V1) */
	public static final String SIS       = "application/vnd.symbian.install-archive";
	/** SISX文件(symbian平台/S60V3/V5) */
	public static final String SISX      = "application/vnd.symbian.install";
	public static final String DLL       = "application/x-msdownload";
	public static final String VCF       = "text/x-vcard";
	public static final String RTF       = "application/rtf";

	/*********** web **********/
	/** c、h、txt 、bas */
	public static final String TEXT      = "text/plain";
	public static final String HTML      = "text/html";
	/**
	 * "application/xml" 和 "text/xml" 二者功能一模一样，唯一的区别就是编码格式.<br/>
	 * text/xml : 忽略xml头所指定编码格式而默认采用us-ascii编码<br/>
	 * application/xml : 会根据xml头指定的编码格式来编码
	 */
	public static final String XML       = "text/xml";
	public static final String CSS       = "text/css";
	public static final String JS        = "application/x-javascript";

	/*********** 压缩文件 **********/
	public static final String ZIP       = "application/zip";
	public static final String GZ        = "application/x-gzip";
	public static final String TGZ       = "application/x-compressed";
	public static final String TAR       = "application/x-tar";
	public static final String GTAR      = "application/x-gtar";

	/*********** 文档 **********/
	public static final String XLS       = "application/vnd.ms-excel";
	public static final String WPS       = "application/vnd.ms-works";
	public static final String PPT       = "application/vnd.ms-powerpoint";
	public static final String PDF       = "application/pdf";
	public static final String DOC       = "application/msword";

	/*********** 图片、动画 **********/
	public static final String GIF       = "image/gif";
	public static final String ICO       = "image/x-icon";
	public static final String JPG       = "image/jpeg";
	public static final String PNG       = "image/png";
	public static final String BMP       = "image/bmp";
	public static final String SVG       = "image/svg+xml";

	/*********** 音频、视频 **********/
	public static final String SWF       = "application/x-shockwave-flash";
	public static final String RM        = "application/vnd.rn-realmedia";
	/** ra、ram */
	public static final String RA        = "audio/x-pn-realaudio";
	public static final String FLV       = "flv/flv-flash";
	public static final String MOD       = "audio/x-mod";
	public static final String MID       = "audio/mid";
	public static final String MIDI      = "audio/midi";
	public static final String AU        = "audio/basic";
	public static final String AAC       = "audio/x-aac";
	public static final String AMR       = "audio/amr";
	public static final String AWB       = "audio/amr-wb";
	public static final String MA1       = "audio/ma1";
	public static final String MA2       = "audio/ma2";
	public static final String MA3       = "audio/ma3";
	public static final String MA5       = "audio/ma5";
	/** m3u、m3url */
	public static final String M3U       = "audio/x-mpegurl";
	/** m4a、m4b、m4p */
	public static final String M4A       = "audio/mp4a-latm";
	/** mp3、mpga */
	public static final String MP3       = "audio/mpeg";
	public static final String WAV       = "audio/x-wav";
	public static final String WAX       = "audio/x-ms-wax";
	public static final String WMA       = "audio/x-ms-wma";
	public static final String WMV       = "audio/x-ms-wmv";
	public static final String OGG       = "audio/ogg";
	public static final String VOX       = "audio/voxware";

	public static final String M4U       = "video/vnd.mpegurl";
	public static final String M4V       = "video/x-m4v";
	public static final String MP4       = "video/mp4";
	public static final String MNG       = "video/x-mng";
	/** mpa、mpe、mpeg 、mpg */
	public static final String MPEG      = "video/mpeg";
	public static final String _3GP      = "video/3gpp";
	public static final String AVI       = "video/x-msvideo";
	public static final String MOV       = "video/quicktime";
	public static final String RMVB      = "application/vnd.rn-realmedia-vbr";

}
