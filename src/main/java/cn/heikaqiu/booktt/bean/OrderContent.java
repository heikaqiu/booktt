package cn.heikaqiu.booktt.bean;

public class OrderContent {

	private Integer id;
	// 单价 因为这是购买时的价格 价格是会变的
	private Float price;
	// 买了几本 可以有多本 数量跟单价对应
	private Integer number;
	//哪本书
	private Book book;
	//哪份订单的
	private Order order;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}


	@Override
	public String toString() {
		return "OrderContent{" +
				"id=" + id +
				", price=" + price +
				", number=" + number +
				", book=" + book +
				", order=" + order +
				'}';
	}
}
