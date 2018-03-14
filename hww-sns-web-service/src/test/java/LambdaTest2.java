import com.alibaba.fastjson.JSON;
import com.google.common.base.Objects;
import com.hww.sns.common.dto.SnsTopicDto;
import com.hww.sns.common.entity.SnsTopic;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LambdaTest2 {

	List<Node> list = new ArrayList<>(16);

	@Before
	public void initial() {
		list.add(new Node(11, "lihao"));
		list.add(new Node(33, "xurongrong"));
		list.add(new Node(22, "liyongquanyangchunlian"));
		list.add(new Node(28, "liguanqing"));
		list.add(new Node(22, "jiangxinrui"));
		list.add(new Node(55, "ddddd"));
		list.add(new Node(22, "今夕何夕"));
	}

	@Test
	public void test1() {
		list.forEach(n -> System.out.println(n));

	}

	@Test
	public void test2() {
		Runnable runnable = () -> list.forEach(n -> System.out.println(n));
		new Thread(runnable).start();

	}

	@Test
	public void test3() {
		list.parallelStream().filter(val -> val.id >= 22).distinct().sorted(Comparator.comparing(Node::getId))
				.forEach(val -> System.out.println(val));

	}

	@Test
	public void test4() {
	    list.parallelStream().map(val->val.getName().toUpperCase()).forEach(val-> System.out.println(val));

	}

	@Test
	public void test5(){
		SnsTopicDto dto=new SnsTopicDto();
		dto.setAddress("北京市朝阳区");
		dto.setTopicId(1231244L);
		dto.setTitle("点点滴滴");
		String str= JSON.toJSONString(dto);
		/*ObjectMapper mapper=new ObjectMapper();
		mapper.readValue(str,Map.class);*/
		Map<String,Object> map=JSON.parseObject(str,Map.class);
		System.out.println(map.get("topicId"));
		Map<String,String>  mval=dto.transferToMap();
		System.out.println(mval.get("topicId"));
		String s=testRetur();
		System.out.println(s);

	}
	private String testRetur(){
		Lock lock=new ReentrantLock();
		lock.lock();
		try {
			return "11";
		} finally {
			lock.unlock();
			return "22";
		}
	}

	private static class Node {
		Integer id;
		String name;

		public Node(Integer id, String name) {
			this.id = id;
			this.name = name;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Node{" + "id=" + id + ", name='" + name + '\'' + '}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Node node = (Node) o;
			return Objects.equal(id, node.id);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(id);
		}
	}
}
