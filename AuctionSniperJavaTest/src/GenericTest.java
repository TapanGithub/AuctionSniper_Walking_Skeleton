import static org.junit.Assert.*;
import static org.hamcrest.Matchers.isIn;

import org.hamcrest.core.AnyOf;
import org.junit.Test;


public class GenericTest {

	static class Parent {
		
	}
	
	static class Child extends Parent {
		
	}

	static class ListStruct<T> {
		
	}

	void foo() {
		Parent p = new Child();
		ListStruct<? extends Number> list = new ListStruct<Integer>();
		ListStruct<? super Number> list1 = new ListStruct<Number>();
	}


	@Test
	public void testName() throws Exception {
		String[] answers = new String[] { "a", "b", "c" };
		assertThat("a", isIn(answers));
		
		assertThat("b", equalTo(anything()));
	}
}
