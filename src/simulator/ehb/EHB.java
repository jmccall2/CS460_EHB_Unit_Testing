package simulator.ehb;

public class EHB
{
//  private BadProfile _profile = new BadProfile();
//  private GoodProfile _profile = new GoodProfile();
//  private GoodClickButton _profile = new GoodClickButton();
  private GoodHoldingButton _profile = new GoodHoldingButton();
//  private StressTest1 _profile = new StressTest1();
//  private StressTest2 _profile = new StressTest2();
//  private StressTest3 _profile = new StressTest3();
//  private StressTest4 _profile = new StressTest4();
//  private StressTest5 _profile = new StressTest5();

  public void update()
  {
    _profile.update();
  }
}
