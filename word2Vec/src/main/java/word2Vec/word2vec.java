package word2Vec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;




import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

public class word2vec {
public static void main (String args[]) throws IOException{
	/*SentenceIterator iter = new LineSentenceIterator(new File("E:/work/Cell_Phones_and_Accessories.txt"));
    iter.setPreProcessor(new SentencePreProcessor() {
        public String preProcess(String sentence) {
            return sentence.toLowerCase();
        }
    });

    System.out.println("Hello");
 // Split on white spaces in the line to get words
    TokenizerFactory t = new DefaultTokenizerFactory();
    t.setTokenPreProcessor(new CommonPreprocessor());
   // log.info("Building model....");
    Word2Vec vec = new Word2Vec.Builder()
            .minWordFrequency(1)
            .iterations(1)
            .layerSize(100)
            .seed(42)
            .windowSize(5)
            .iterate(iter)
            .tokenizerFactory(t)
            .build();

   // log.info("Fitting Word2Vec model....");
    vec.fit();
    WordVectorSerializer.writeFullModel(vec, "pathToSaveModelnew.txt");
    
    
    double cosSim = vec.similarity("photo", "picture");
    System.out.println(cosSim);
    cosSim = vec.similarity("audio", "sound");
    System.out.println(cosSim);
    Collection<String> lst3 = vec.wordsNearest("audio", 10);
    System.out.println(lst3);*/
	
	//part 2

	   
	Word2Vec vec = WordVectorSerializer.loadFullModel("pathToSaveModelnew.txt"); 
	
	
	String mat[][]=new String[][]{{"general","packaging","model","colour","color","sim"},
			   {"display","resolution","pixel","led","LED","touchscreen","screen"},
			   {"os","operating","system","processor","frequency","configuration","config"},
			   {"memory","storage","internal","RAM"},
			   {"camera","primary","secondary","recording","video","hd","picture"},
			   {"connectivity","network","internet","3G","GPRS","GPS","bluetooth","wifi","USB"},
			   {"multimedia","audio","video","formats"},
			   {"dimension","height","width","depth","size"},
			   {"battery","sensors","smartphone","JAVA","support","others"}
			   };  
	
	
	
	 String str="E:\\work\\Deepanshu\\OUTPUT_CORPORA\\annote.txt";  //Set the path for correctly formatted review data file. 
		BufferedReader in= new BufferedReader(new FileReader(str));
		String sCurrentLine;
		int max=Integer.MIN_VALUE;
		while((sCurrentLine = in.readLine()) != null){
			int reviewid=Integer.parseInt(sCurrentLine.substring(0, sCurrentLine.indexOf('@')));
			if(reviewid>max)
				max=reviewid;
		}
		System.out.println(max);
	  double maxval=Double.MIN_VALUE;
	   double maxval1=Double.MIN_VALUE;
	   int index=mat.length-1;
	   int featureVec[][]=new int[max][mat.length];
	   for(int i=0;i<featureVec.length;i++)
		   for(int j=0;j<featureVec[0].length;j++)
			   featureVec[i][j]=0;
	 str="E:\\work\\Deepanshu\\OUTPUT_CORPORA\\annote.txt";  //Set the path for correctly formatted review data file. 
	 in= new BufferedReader(new FileReader(str));
	
	while((sCurrentLine = in.readLine()) != null){
		int reviewid=Integer.parseInt(sCurrentLine.substring(0, sCurrentLine.indexOf('@')));
	String reviewText=sCurrentLine.substring(sCurrentLine.indexOf('@')+1,sCurrentLine.lastIndexOf('@') );
	
	
	
	  
		   if(vec.hasWord(reviewText)==false){
			   featureVec[reviewid-1][mat.length-1]=1;
			   continue;
		   }
		   index=mat.length-1;
		   maxval1=Double.MIN_VALUE;
		   for(int i=0;i<mat.length;i++){
			   maxval=Double.MIN_VALUE;
			   for(int k=0;k<mat[i].length;k++){
				   if(vec.hasWord(mat[i][k])==false)
					   continue;
				   double val=vec.similarity(reviewText,mat[i][k]);
				   if(val>maxval)
					   maxval=val;
					   
			   }
			   if(maxval>maxval1){
				   maxval1=maxval;
			   		index=i;
		   }
		   }
		 featureVec[reviewid-1][index]=1;
	   
	
	}
	
	
	for(int i=0;i<featureVec.length;i++){
		   for(int j=0;j<featureVec[0].length;j++)
			   System.out.print(featureVec[i][j]+" ");
		   System.out.println();
	}
	  
	

}
}
