

public class MMansionSim 
{
	static int reqScore = 33770;
	static int score;
	static int maxL;
	static int[] amt = {1,2,4,8,15,30,60,120,250,500,1000,2000};
	static int eggs;
	static int eggTaps;
	
	//items
	static int bike;
	static int trapDoor;
	static int wardrobe;
	static int decorBox;
	
	//static int napkins;
	
	static int puzzle;
	static int puzzleTap;
	static int videoGame;
	
	static int jewelBox;
	static int jewelBoxTaps;
	static int eggJewel;
	static int basket;
	static int basketTaps;
	
	static boolean bagAvail;
	
	static int bunnySuit;
	//static int bunnySuitTaps;
	
	//static int[] otherProd = {0,0,0};  //puzzle, 
	static int[] eggGenCount = {0,0,0,0,0,0,0,0};  //puzzles, video games, jewel boxes, egg jewels, bunny suits, bikes, trap doors, wardrobes
	static int[] milestones = {0,0,0};   //q,h,f
	
	public static void main(String[] args) 
	{
		fullySimulate(true, true);
		
		reportGenCount();
		//simulate2(true);
	}
	
	public static void reportGenCount()
	{
		String[] names = {"Puzzles", "Video Games", "Jewel Boxes", "Egg Jewels", "Bunny Suits", "Bikes", "Trap Doors", "Wardrobes"};
		for(int i = 0; i < names.length; i++)
			System.out.println(names[i] + ":" + eggGenCount[i]);
		String[] ms = {"quart egg" , "half egg", "full egg"};
		for(int i = 0; i < ms.length; i++)
			System.out.println(ms[i] + ":" + milestones[i]);
	}
	
	public static void simulate2(boolean allowGarage)
	{
		int totalTaps = 0;
		int subAvg = 0;
		int subAvg2 = 0;
		int min = 99999;
		int max = 0;
		for(int i = 0; i < 1000; i++)
		{
			int nTaps = fullySimulate(allowGarage, false);
			totalTaps += nTaps;
			if(nTaps > max)
				max = nTaps;
			if(nTaps < min)
				min = nTaps;
			if(i % 100 == 99)
			{
				subAvg = totalTaps - subAvg2;
				subAvg2 = totalTaps;
				int hundA = (int) (subAvg / 100);
				System.out.println("Subtotal " + (i + 1) + " required " + hundA + " taps");
			}
		}
		int avgTaps = (int) (totalTaps / 1000);
		System.out.println("Score " + reqScore + " required an average of " + avgTaps + " total taps; min=" + min + " max=" + max);
	}
	
	public static void simulate1()
	{
		int totalTaps = 0;
		int subAvg = 0;
		int subAvg2 = 0;
		int min = 99999;
		int max = 0;
		for(int i = 0; i < 1000; i++)
		{
			int nTaps = simulate();
			totalTaps += nTaps;
			if(nTaps > max)
				max = nTaps;
			if(nTaps < min)
				min = nTaps;
			if(i % 100 == 99)
			{
				subAvg = totalTaps - subAvg2;
				subAvg2 = totalTaps;
				int hundA = (int) (subAvg / 100);
				System.out.println("Subtotal " + (i + 1) + " required " + hundA + " egg taps");
			}
		}
		int avgTaps = (int) (totalTaps / 1000);
		System.out.println("Score " + reqScore + " required an average of " + avgTaps + " egg taps; min=" + min + " max=" + max);
	}
	
	public static int simulate()
	{
		int taps = 0;
		score = 0;
		maxL = 5;
		eggs = 0;
		//int reqScore = 33770;
		while(score < reqScore)
		{
			taps++;
			tapEggs();
		}
		
		return taps;
	}
	
	public static int fullySimulate(boolean allowGarage, boolean report)
	{
		int taps = 0;
		int ets = 0;
		score = 0;
		maxL = 5;
		eggs = 0;
		eggTaps = 0;
		bike = 0;
		trapDoor = 0;
		wardrobe = 0;
		decorBox = 0;
		
		//static int napkins;
		
		puzzle = 0;
		puzzleTap = 0;
		videoGame = 0;
		
		jewelBox = 0;
		jewelBoxTaps = 0;
		eggJewel = 0;
		basket = 0;
		basketTaps = 0;
		
		bagAvail = false;
		
		bunnySuit = 0;
		while(score < reqScore)
		{
			taps++;
			//what to tap
			if(eggTaps > 0)
			{
				tapEggs();
				eggTaps--;
				ets++;
			}
			if(puzzleTap > 0)
			{
				puzzleTap--;
				puzzle += tap();
				if(puzzle >= 64)
				{
					puzzle -= 64;
					eggTaps += 12;
					eggGenCount[0]++;
				}
				if(puzzleTap == 0)
				{
					videoGame++;
					if(videoGame >= 8)
					{
						videoGame -= 8;
						eggTaps += 34;
						eggGenCount[1]++;
					}
				}
			}
			if(basketTaps > 0)
			{
				basketTaps--;
				bunnySuit += tap();
				if(bunnySuit >= 128)
				{
					bunnySuit -= 128;
					eggTaps += 18;
					eggGenCount[4]++;
				}
			}
			if(jewelBoxTaps > 0)
			{
				jewelBoxTaps--;
				jewelBox += tap();
				if(jewelBox >= 128)
				{
					jewelBox -= 128;
					eggTaps += 5;
					eggJewel++;
					eggGenCount[2]++;
					if(eggJewel >= 32)
					{
						eggJewel -= 32;
						eggTaps += 124;
						eggGenCount[3]++;
						if(milestones[2] == 0)
							milestones[2] = taps;
					}
					if(eggJewel >= 8 && milestones[0] == 0)
						milestones[0] = taps;
					else if(eggJewel >= 16 && milestones[1] == 0)
						milestones[1] = taps;
				}
				if(jewelBoxTaps == 0)
				{
					basket++;
					if(basket >= 16)
					{
						basket -= 16;
						basketTaps += 120;
					}
				}
			}
			if(bagAvail)
				tapGarage();
			else
				tapWardrobe(allowGarage);
		}
		if(report)
			System.out.println("A score of " + reqScore + " needed " + taps + " taps; " + ets + " egg taps");
		return taps;
	}
	
	private static int tap()
	{
		int r = (int) (Math.random() * 3);
		//r++;
		return 1 << r;
	}
	
	public static void tapGarage()
	{
		double r = Math.random();
		if(r < 0.75)  //bike piece
		{
			bike += tap();
			if(bike >= 512)
			{
				bike -= 512;
				eggTaps += 10;
				eggGenCount[5]++;
			}
		}
		//else if(r > 0.99)
			//napkins += tap();
		else
		{
			trapDoor += tap();
			if(trapDoor >= 64)
			{
				trapDoor -= 64;
				puzzleTap += 28;
				bagAvail = false;
				eggGenCount[6]++;
			}
		}	
	}
	
	public static void tapWardrobe(boolean allowGarage)
	{
		double r = Math.random();
		if(r < 0.9)  //wardrobe piece
		{
			wardrobe += tap();
			if(wardrobe >= 128)
			{
				wardrobe -= 128;
				eggTaps += 3;
				if(allowGarage)
					bagAvail = true;
				eggGenCount[7]++;
			}
		}
		//else if(r > 0.99)
			//napkins += tap();
		else
		{
			decorBox++;
			if(decorBox >= 16)
			{
				decorBox -= 16;
				jewelBoxTaps += 54;
			}
		}
	}
	
	/*public static void tapOther(int o)
	{
		switch(o)
		{
		case 0://bike
			
		}
	}*/
	
	public static void tapEggs()
	{
		int drop = (int) (Math.random() * 3);
		drop++;
		//score from drop
		score += amt[drop - 1];
		//System.out.print("D" + drop + "  S" + score);
		while(true)
		{
			//System.out.print("  E" + Long.toBinaryString(eggs));
			int t = 1 << (drop - 1);
			if((eggs & t) == 0)
			{
				
				if(drop < maxL)
					eggs |= t;
				else  //level up the egg if possible
				{
					score += amt[drop];
					//System.out.print(" S" + score);
					if(maxL < 11)
					{
						//System.out.print(" MxLUP ");
						maxL++;
					}
				}
				//System.out.print(" N  E" + Long.toBinaryString(eggs));
				break;
			}
			//System.out.print(" M" + drop);
			eggs ^= t;
			//System.out.print("  E" + Long.toBinaryString(eggs));
			//you can merge and the drop becomes the new value
			drop++;
			//score from the merge
			score += amt[drop - 1];
			//System.out.print(" S" + score);
		}
		//System.out.println();
		//return eggs;
	}
	

}
