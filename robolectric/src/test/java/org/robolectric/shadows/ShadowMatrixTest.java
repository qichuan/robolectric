package org.robolectric.shadows;

import android.graphics.Matrix;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.TestRunners;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.robolectric.Shadows.shadowOf;

@RunWith(TestRunners.WithDefaults.class)
public class ShadowMatrixTest {
  @Test
  public void preOperationsAreStacked() {
    Matrix m = new Matrix();
    m.preRotate(4, 8, 15);
    m.preTranslate(16, 23);
    m.preSkew(42, 108);

    assertThat(shadowOf(m).getPreOperations()).containsExactly(
        "skew 42.0 108.0",
        "translate 16.0 23.0",
        "rotate 4.0 8.0 15.0"
    );
  }

  @Test
  public void postOperationsAreQueued() {
    Matrix m = new Matrix();
    m.postRotate(4, 8, 15);
    m.postTranslate(16, 23);
    m.postSkew(42, 108);

    assertThat(shadowOf(m).getPostOperations()).containsExactly(
        "rotate 4.0 8.0 15.0",
        "translate 16.0 23.0",
        "skew 42.0 108.0"
    );
  }

  @Test
  public void setOperationsOverride() {
    Matrix m = new Matrix();
    m.setRotate(4);
    m.setRotate(8);
    m.setRotate(15);
    m.setRotate(16);
    m.setRotate(23);
    m.setRotate(42);
    m.setRotate(108);

    assertThat(shadowOf(m).getSetOperations()).contains(entry("rotate", "108.0"));
  }
  
  @Test
  public void testSetNullParam(){
      Matrix m = new Matrix();
      try{
      	shadowOf(m).set(null);
      }
      catch(NullPointerException e){
      	fail("NullPointerException in shadowMatrix.set()");
      }
	  assertTrue(true);
  }
}
