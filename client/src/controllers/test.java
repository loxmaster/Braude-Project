package controllers;
import java.util.Objects;

public class test {
	private String lmao;

	public test() {
	}

	public test(String lmao) {
		this.lmao = lmao;
	}

	public String getLmao() {
		return this.lmao;
	}

	public void setLmao(String lmao) {
		this.lmao = lmao;
	}

	public test lmao(String lmao) {
		setLmao(lmao);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof test)) {
			return false;
		}
		test test = (test) o;
		return Objects.equals(lmao, test.lmao);
	}

	@Override
	public String toString() {
		return "{" +
			" lmao='" + getLmao() + "'" +
			"}";
	}
	
}
