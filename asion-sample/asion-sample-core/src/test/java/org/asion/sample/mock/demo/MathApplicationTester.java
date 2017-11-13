package org.asion.sample.mock.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// @RunWith attaches a runner with the test class to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class MathApplicationTester {

    //@InjectMocks annotation is used to create and inject the mock object
   @InjectMocks
   private MathApplication mathApplication = new MathApplication();

   //@Mock annotation is used to create the mock object to be injected
   @Mock
   private CalculatorService calcService;

   @Before
   public void setUp(){
      mathApplication = new MathApplication();
      Calculator calculator = new Calculator();
      calcService = spy(calculator);
      mathApplication.setCalculatorService(calcService);
   }



   @Test
   public void testAdd(){
      // 添加行为
      //add the behavior of calc service to add two numbers
      when(calcService.add(10.0,20.0)).thenReturn(30.00);
      given(calcService.add(10.0,20.0)).willReturn(30.00);

      // 测试行为
      //test the add functionality
      assertThat(mathApplication.add(10.0, 20.0)).isEqualTo(30.0);

      //verify call to calcService is made or not with same arguments.
      verify(calcService).add(10.0, 20.0);
   }

   class Calculator implements CalculatorService {
      @Override
      public double add(double input1, double input2) {
         return input1 + input2;
      }

      @Override
      public double subtract(double input1, double input2) {
         throw new UnsupportedOperationException("Method not implemented yet!");
      }

      @Override
      public double multiply(double input1, double input2) {
         throw new UnsupportedOperationException("Method not implemented yet!");
      }

      @Override
      public double divide(double input1, double input2) {
         throw new UnsupportedOperationException("Method not implemented yet!");
      }
   }
}