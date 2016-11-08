package cn.springmvc.model;

public class TagModel {
	int id;
	String name;
	int count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "TagModel [id=" + id + ", name=" + name + ", count=" + count + "]";
	}

}
