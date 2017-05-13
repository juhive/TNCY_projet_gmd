package main;

import java.util.ArrayList;

import databases.SiderConnexion;
import databases.StitchConnexion;

public class MappingEvaluation {

	private static SiderConnexion sidderCo = new SiderConnexion();
	private static StitchConnexion stitchCo = new StitchConnexion();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	public static int meddraAllSeCompoundId1_StitchCompoundId() {
		ArrayList<String> compound_id1_list = sidderCo.sidder_meddra_all_se_compound_id1();
		int nbrHits = 0;
		
		for (int i = 0; i<compound_id1_list.size(); i++){
			if (stitchCo.meddraAllSeCompoundId1_in_StitchCodeAtc(compound_id1_list.get(i))) {
				nbrHits++;
			}
		}
		return nbrHits;
	}
	
}
