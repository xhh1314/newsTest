package SimpleTest;

import com.alibaba.fastjson.JSONObject;

public class HttpTest {

	public static String revertMapUrl = "http://api.map.baidu.com/geocoder/v2/?callback={callback}&output= {output}&pois= {pois}&ak= {ak}&location= {location}";

	public static void praseResult() {
		String result="renderReverse&&renderReverse({\"status\":0,\"result\":{\"location\":{\"lng\":116.38069699999993,\"lat\":39.88958407902157},\"formatted_address\":\"北京市西城区菜市口胡同36号\",\"business\":\"菜市口,陶然亭,牛街\",\"addressComponent\":{\"country\":\"中国\",\"country_code\":0,\"country_code_iso\":\"CHN\",\"province\":\"北京市\",\"city\":\"北京市\",\"city_level\":2,\"district\":\"西城区\",\"town\":\"\",\"adcode\":\"110102\",\"street\":\"菜市口胡同\",\"street_number\":\"36号\",\"direction\":\"附近\",\"distance\":\"23\"},\"pois\":[{\"addr\":\"西城区菜市口大街平原里20-2号\",\"cp\":\" \",\"direction\":\"北\",\"distance\":\"148\",\"name\":\"中国建设银行(菜市口南街储蓄所)\",\"poiType\":\"金融\",\"point\":{\"x\":116.38019490175145,\"y\":39.888633607835469},\"tag\":\"金融;银行\",\"tel\":\"\",\"uid\":\"a3f21eee4c0aed452fb17e57\",\"zip\":\"\",\"parent_poi\":{\"name\":\"陶然居写字楼\",\"tag\":\"房地产;写字楼\",\"addr\":\"西城区菜市口南大街西侧\",\"point\":{\"x\":116.38024880008205,\"y\":39.88842599900448},\"direction\":\"北\",\"distance\":\"174\",\"uid\":\"3a0a78bdd20714fa631809f0\"}},{\"addr\":\"西城区菜市口南大街西侧\",\"cp\":\" \",\"direction\":\"北\",\"distance\":\"174\",\"name\":\"陶然居写字楼\",\"poiType\":\"房地产\",\"point\":{\"x\":116.38024880008205,\"y\":39.88842599900448},\"tag\":\"房地产;写字楼\",\"tel\":\"\",\"uid\":\"3a0a78bdd20714fa631809f0\",\"zip\":\"\",\"parent_poi\":{\"name\":\"\",\"tag\":\"\",\"addr\":\"\",\"point\":{\"x\":0.0,\"y\":0.0},\"direction\":\"\",\"distance\":\"\",\"uid\":\"\"}},{\"addr\":\"北京西城区南横西街31号近陶然亭站\",\"cp\":\" \",\"direction\":\"东\",\"distance\":\"183\",\"name\":\"合全居聚鑫烤鸭店\",\"poiType\":\"美食\",\"point\":{\"x\":116.37905405375378,\"y\":39.88950555800495},\"tag\":\"美食;中餐厅\",\"tel\":\"\",\"uid\":\"373cf757b9bf1d774254d7ab\",\"zip\":\"\",\"parent_poi\":{\"name\":\"\",\"tag\":\"\",\"addr\":\"\",\"point\":{\"x\":0.0,\"y\":0.0},\"direction\":\"\",\"distance\":\"\",\"uid\":\"\"}},{\"addr\":\"北京市西城区菜市口大街56号\",\"cp\":\" \",\"direction\":\"西北\",\"distance\":\"229\",\"name\":\"国网科技馆\",\"poiType\":\"教育培训\",\"point\":{\"x\":116.38164117362251,\"y\":39.88816994724011},\"tag\":\"教育培训;科技馆\",\"tel\":\"\",\"uid\":\"46d1eb26846b442b2bb97c57\",\"zip\":\"\",\"parent_poi\":{\"name\":\"\",\"tag\":\"\",\"addr\":\"\",\"point\":{\"x\":0.0,\"y\":0.0},\"direction\":\"\",\"distance\":\"\",\"uid\":\"\"}},{\"addr\":\"西城区菜市口大街甲2号院5号楼底商5-18链家地产\",\"cp\":\" \",\"direction\":\"西南\",\"distance\":\"211\",\"name\":\"链家地产(前兵马街)\",\"poiType\":\"生活服务\",\"point\":{\"x\":116.38153337696132,\"y\":39.89089650299064},\"tag\":\"生活服务;房产中介机构\",\"tel\":\"\",\"uid\":\"6b617c1e29f5c3542811d107\",\"zip\":\"\",\"parent_poi\":{\"name\":\"\",\"tag\":\"\",\"addr\":\"\",\"point\":{\"x\":0.0,\"y\":0.0},\"direction\":\"\",\"distance\":\"\",\"uid\":\"\"}},{\"addr\":\"广安门内街道菜市口大街平原里小区20号楼\",\"cp\":\" \",\"direction\":\"北\",\"distance\":\"235\",\"name\":\"陶然居\",\"poiType\":\"房地产\",\"point\":{\"x\":116.38023083397185,\"y\":39.88799693874546},\"tag\":\"房地产;写字楼\",\"tel\":\"\",\"uid\":\"edb38faf181945554251c0e2\",\"zip\":\"\",\"parent_poi\":{\"name\":\"\",\"tag\":\"\",\"addr\":\"\",\"point\":{\"x\":0.0,\"y\":0.0},\"direction\":\"\",\"distance\":\"\",\"uid\":\"\"}},{\"addr\":\"北京市西城区菜市口大街甲2号院6号楼\",\"cp\":\" \",\"direction\":\"西南\",\"distance\":\"269\",\"name\":\"中信银行(中信城支行)\",\"poiType\":\"金融\",\"point\":{\"x\":116.38177591944901,\"y\":39.89125634482247},\"tag\":\"金融;银行\",\"tel\":\"\",\"uid\":\"b546a26e6990e945081cc71d\",\"zip\":\"\",\"parent_poi\":{\"name\":\"\",\"tag\":\"\",\"addr\":\"\",\"point\":{\"x\":0.0,\"y\":0.0},\"direction\":\"\",\"distance\":\"\",\"uid\":\"\"}},{\"addr\":\"南横西街51号(法源寺东边)\",\"cp\":\" \",\"direction\":\"东\",\"distance\":\"274\",\"name\":\"蚝情壮翅\",\"poiType\":\"美食\",\"point\":{\"x\":116.3782365957397,\"y\":39.889491717613399},\"tag\":\"美食;中餐厅\",\"tel\":\"\",\"uid\":\"06d2dffdaf0db6ef89f15da8\",\"zip\":\"\",\"parent_poi\":{\"name\":\"\",\"tag\":\"\",\"addr\":\"\",\"point\":{\"x\":0.0,\"y\":0.0},\"direction\":\"\",\"distance\":\"\",\"uid\":\"\"}},{\"addr\":\"西城区菜市口南大街平原里21号\",\"cp\":\" \",\"direction\":\"北\",\"distance\":\"329\",\"name\":\"乐秀商城\",\"poiType\":\"购物\",\"point\":{\"x\":116.38015896953105,\"y\":39.88733950245206},\"tag\":\"购物;其他\",\"tel\":\"\",\"uid\":\"1416f51adfc8491115c9051a\",\"zip\":\"\",\"parent_poi\":{\"name\":\"\",\"tag\":\"\",\"addr\":\"\",\"point\":{\"x\":0.0,\"y\":0.0},\"direction\":\"\",\"distance\":\"\",\"uid\":\"\"}},{\"addr\":\"北京市西城区菜市口南大街58号\",\"cp\":\" \",\"direction\":\"北\",\"distance\":\"332\",\"name\":\"欣燕都(菜市口店)\",\"poiType\":\"酒店\",\"point\":{\"x\":116.38157829223681,\"y\":39.8873879453432},\"tag\":\"酒店;快捷酒店\",\"tel\":\"\",\"uid\":\"e56bf5e3f71d8c5d0782c205\",\"zip\":\"\",\"parent_poi\":{\"name\":\"\",\"tag\":\"\",\"addr\":\"\",\"point\":{\"x\":0.0,\"y\":0.0},\"direction\":\"\",\"distance\":\"\",\"uid\":\"\"}}],\"roads\":[],\"poiRegions\":[],\"sematic_description\":\"中国建设银行(菜市口南街储蓄所)北148米\",\"cityCode\":131}})";
		result = result.substring(result.indexOf("{"), result.lastIndexOf("}")+1);
		JSONObject obj = JSONObject.parseObject(result);
		String status = obj.getString("status");
		if(status.equals("0")) {
			JSONObject resultObj = obj.getJSONObject("result");
			String location = resultObj.getString("formatted_address");
			System.out.println(location);
		}
		
	}
	public static void main(String args[]) {
        //入参
//        Map<String,Object> uriVariables = new HashMap<String,Object>();
//        uriVariables.put("callback", "renderReverse");
//        uriVariables.put("output", "json");
//        uriVariables.put("pois", "1");
//        uriVariables.put("ak", "40bcbd6c7361485243a9c1e30d60d6cc");
//        uriVariables.put("location", "39.9155"+","+"116.3998");
//        RestTemplate restTemplate = new RestTemplate();
//        String responseData = restTemplate.getForObject(revertMapUrl, String.class, uriVariables);
//        System.out.println(responseData);
		praseResult();
	}
}
