package in.risk.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestMapLoader.class, TestPlayer.class, TestStartUpPhase.class })
public class AllTests {

}
