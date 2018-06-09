//package Trimer3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CommonClass {
	//Set output format.
    public static String Changeformat(double value) 
    { 
         DecimalFormat df = new DecimalFormat("0.00");
         df.setRoundingMode(RoundingMode.HALF_UP);
         return df.format(value);
    }
	//Get the lines of kMER.
    public static int getLineOfKmer(String KmerSetPath) throws IOException
    {
         int line=0; 
         String encoding = "utf-8";
         File file = new File(KmerSetPath);
         if (file.isFile() && file.exists()) 
         { 
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
            BufferedReader bufferedReader = new BufferedReader(read);
            while ((bufferedReader.readLine()) != null)
            { 
    		    line++; 
            } 
            bufferedReader.close();
         }
         return line;
    }
	//Get the lines of File.
    public static int getFileLine(String ReadSetPath) throws IOException
    {
            int line=0; 
            String encoding = "utf-8";
            File file = new File(ReadSetPath);
            if (file.isFile() && file.exists()) 
            { 
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
                BufferedReader bufferedReader = new BufferedReader(read);
                while ((bufferedReader.readLine()) != null)
                { 
    		    	line++; 
                } 
                bufferedReader.close();
           }
           return line;
    }
	//Get lines of FASTQ.
    public static int getLineOfFastq(String FastqFilePath) throws IOException
    {
         int line=0; 
         String encoding = "utf-8";
         File file = new File(FastqFilePath);
         if (file.isFile() && file.exists()) 
         { 
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
            BufferedReader bufferedReader = new BufferedReader(read);
            while ((bufferedReader.readLine()) != null)
            { 
    		    line++; 
            } 
            bufferedReader.close();
         }
         return line/4;
    }
	//Get the average quality score of read.
	public static double GetAverageQualityScoreOfRead(String QualityStringofRead)
	{
	     int Sum=0;
		 for (int k=0; k<QualityStringofRead.length(); k++)
	     {
		    Sum+=(int)(QualityStringofRead.charAt(k));
	     }
	     return (double)Sum/QualityStringofRead.length();
	}
	//Check the low quality base of read.
	public static double CheckLowBase(String QualityStringofRead)
	{
	     int Sum=0;
		 for (int k=0; k<QualityStringofRead.length(); k++)
	     {
		    if(QualityStringofRead.charAt(k)=='#')
		    {
		    	Sum++;
		    }
	     }
	     return (double)Sum/QualityStringofRead.length();
	}
	//Put the ReadSet into Array.
	public static int readTxtFile(String ReadSetPath, String[] ReadSetArray) 
	{
		int count = 0;
		try {
			  String encoding = "utf-8";
			  File file = new File(ReadSetPath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // set format of encoding.
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((bufferedReader.readLine())!= null) {
						  ReadSetArray[count++]=bufferedReader.readLine();
						  bufferedReader.readLine();
						  bufferedReader.readLine();
				  }
				  bufferedReader.close();
			  } 
			  else {
				  System.out.println("File is not exist!");
			  }
		} catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
		return count;
	}
	//Generate FASTA Array.
	public static int FastaToArray(String FastaPath, String[] FastaArray) throws IOException 
	{
		int ReadCount=0;
		String encoding = "utf-8";
		try {
			  File file = new File(FastaPath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((bufferedReader.readLine()) != null) 
				  {
                      FastaArray[ReadCount++]=bufferedReader.readLine();
				  }
				  bufferedReader.close();
			  } 
			  else 
			  {
				  System.out.println("File is not exist!");
			  }
		} catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
		return ReadCount;
	}
	//Generate FastqFile array.
	public static int FastqFile_Array(String FastqFilePath, String[] ReadAndQSArray)
	{
		int Index = 0;
		int CountElements=0;
		try {
			  String encoding = "utf-8";
			  File file = new File(FastqFilePath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((bufferedReader.readLine()) != null) {
					  String Line1=bufferedReader.readLine();
					  bufferedReader.readLine();
					  String Line2=bufferedReader.readLine();
					  ReadAndQSArray[CountElements++]=(Index++)+"\t"+Line1+"\t"+Line2+"\t"+Changeformat(GetAverageQualityScoreOfRead(Line2));
				  }
				  bufferedReader.close();
			  } 
			  else {
				  System.out.println("File is not exist!");
			  }
		} catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
		return CountElements;
	}
	//Generate PEReadSet array.
	public static int PEReadSetArray(String FastqLeftPath, String FastqRightPath, String[] ReadAndQSArray)
	{
		int CountElements=0;
		try {
			  String encoding = "utf-8";
			  //Left.
			  File file1 = new File(FastqLeftPath);
			  if (file1.isFile() && file1.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file1), encoding);
				  BufferedReader bufferedReader1 = new BufferedReader(read);
				  while ((bufferedReader1.readLine()) != null) {
					  ReadAndQSArray[CountElements++]=bufferedReader1.readLine();
					  bufferedReader1.readLine();
					  bufferedReader1.readLine();
				  }
				  bufferedReader1.close();
			  } 
			  else 
			  {
				  System.out.println("File is not exist!");
			  }
			  //Right.
			  File file2 = new File(FastqRightPath);
			  if (file2.isFile() && file2.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file2), encoding);
				  BufferedReader bufferedReader2 = new BufferedReader(read);
				  while ((bufferedReader2.readLine()) != null) {
					  ReadAndQSArray[CountElements++]=bufferedReader2.readLine();
					  bufferedReader2.readLine();
					  bufferedReader2.readLine();
				  }
				  bufferedReader2.close();
			  } 
			  else 
			  {
				  System.out.println("File is not exist!");
			  }
  
		} catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
		return CountElements;
	}
    //Generate File Array.
	public static int FileToArray(String FilePath, String[] FileArray) throws IOException 
	{
		int ReadCount=0;
		String encoding = "utf-8";
		try {
			  String readtemp ="";
			  File file = new File(FilePath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((readtemp = bufferedReader.readLine()) != null) 
				  {
						FileArray[ReadCount++]=readtemp;
				  }
				  bufferedReader.close();
			  } 
			  else 
			  {
				  System.out.println("File is not exist!");
			  }
		} catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
		return ReadCount;
	}
	//Reverse
	public static String reverse(String s) 
	{
		int length=s.length();
		String reverse = "";
		for (int i=0;i<length;i++)
		{
			 if(s.charAt(i)=='A')
			 {
		         reverse="T"+reverse;
			 }
			 else if(s.charAt(i)=='T')
			 {
		         reverse="A"+reverse;
			 }
			 else if(s.charAt(i)=='G')
			 {
		         reverse="C"+reverse;
			 }
			 else if(s.charAt(i)=='C')
			 {
		         reverse="G"+reverse;
			 }
			 else
			 {
				 reverse="N"+reverse;
			 }
	    }
	    return reverse;
	}
    //Hash Function.
	public static int RSHash(String str)
    {
        int hash = 0; 
        for(int i=0;i<str.length();i++)
        {
            hash = str.charAt(i)+ (hash << 6) + (hash << 16) - hash;
        }
        return (hash & 0x7FFFFFFF);
    }
 	//Get element From HashTable.
	public static int getHashUnit(String KmerStr,String HashTable[][],int SizeOfDSK)
    {
		int i=1;
		int hashcode=RSHash(KmerStr)%SizeOfDSK;
    	if(HashTable[hashcode][0]!=null)
		{
			if(HashTable[hashcode][0].equals(KmerStr))
    	    {
    		     return hashcode;
			}
    	    else
    	    {
			     while(HashTable[(hashcode+i)%SizeOfDSK][0]!=null)
			     {
			    	 if(HashTable[(hashcode+i)%SizeOfDSK][0].equals(KmerStr))
			    	 {
			    		 return (hashcode+i)%SizeOfDSK;
			    	 }
			    	 else
			    	 {
                         i++;
			    	 }
			     }
    	     }
		}
		return -1;
    }
    //KMER File To HashTable.
    public static int KmerFileToHash(String KmerFilePath, String[][] KmerHashTable,int SizeOfHash) throws IOException 
    {
    	int countDSK=0;
    	String encoding = "utf-8";
    	try {
    		  String readtemp ="";
    		  File file = new File(KmerFilePath);
    		  if (file.isFile() && file.exists()) {
    			  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
    			  BufferedReader bufferedReader = new BufferedReader(read);
    			  while ((readtemp = bufferedReader.readLine()) != null) 
    			  {
					  if(readtemp.length()>0)
					  {
						  String [] SplitLine = readtemp.split("\t|\\s+");
					      int hashCode=RSHash(SplitLine[0])%SizeOfHash;
					      if(KmerHashTable[hashCode][0]==null)
					      {
					          KmerHashTable[hashCode][0]=SplitLine[0];
					          KmerHashTable[hashCode][1]=SplitLine[1];
					      }
					      else
					      {
						      int i=1;
					          while(KmerHashTable[(hashCode+i)%SizeOfHash][0]!= null)
						      {    
	                              i++;							 
	                          }
					          if(KmerHashTable[(hashCode+i)%SizeOfHash][0]==null)
					          {
						          KmerHashTable[(hashCode+i)%SizeOfHash][0]=SplitLine[0];
						          KmerHashTable[(hashCode+i)%SizeOfHash][1]=SplitLine[1];
					          }
					      }
                          countDSK++;
				          if(countDSK%10000000==0)
					      {
						     System.out.println("P-rate:"+Changeformat((double)countDSK/(SizeOfHash-100000)*100)+"%");
					      }	
					   }
    			   }
    			   bufferedReader.close();
    		   } 
    		   else 
    		   {
    			   System.out.println("File is not exist!");
    		   }
    	 } catch (Exception e) {
    		System.out.println("Error liaoxingyu");
    		e.printStackTrace();
    	 }
    	 return countDSK;
     }
	 //Calculate quality score of window.
	 public static double CalculateAverageQualityScore(String KmerString) throws IOException
	 { 
		 double qualityscore=0;
		 double Product=1;
		 for (int k=0; k<KmerString.length(); k++)
	     {
			int value=(int)(KmerString.charAt(k));
			Product=Product*(1-Math.pow(10, -value/10));
	     }
		 qualityscore=1-Product;
		 return qualityscore;
	 } 
	 //Calculate GC content of window.
	 public static double CalculateAverageGCcontent(String KmerString) throws IOException
	 { 
		 double qualityscore=0;
		 double Product=0;
		 for (int k=0; k<KmerString.length(); k++)
	     {
			if(KmerString.charAt(k)=='G'||KmerString.charAt(k)=='C');
			{
				Product++;
			}
	     }
		 qualityscore=Product/KmerString.length();
		 return qualityscore;
	 } 
	 //Calculate k-mer frequency.
	 public static double CalculateAverageKmerScore(String KmerString, String KmerHashTable_A[][], int SizeOfKmerHashTable_A, String KmerHashTable_T[][], int SizeOfKmerHashTable_T, String KmerHashTable_G[][], int SizeOfKmerHashTable_G, String KmerHashTable_C[][], int SizeOfKmerHashTable_C) throws IOException
	 { 
	     double KmerScore=0;
	     String FR_KmerStr=KmerString;
	     String RE_KmerStr=reverse(KmerString);
		 int HasHAddress_RE=-1;
		 int HasHAddress_FR=-1;
	     if(RE_KmerStr.charAt(0)=='A')
	     {
			 HasHAddress_RE=CommonClass.getHashUnit(reverse(KmerString),KmerHashTable_A,SizeOfKmerHashTable_A);
			 if(HasHAddress_RE!=-1)
		     {
				 KmerScore=(double)1/Integer.parseInt(KmerHashTable_A[HasHAddress_RE][1]);
				 return KmerScore;
		     }
	     }
	     if(RE_KmerStr.charAt(0)=='T')
	     {
			 HasHAddress_RE=CommonClass.getHashUnit(reverse(KmerString),KmerHashTable_T,SizeOfKmerHashTable_T);
			 if(HasHAddress_RE!=-1)
		     {
				 KmerScore=(double)1/Integer.parseInt(KmerHashTable_T[HasHAddress_RE][1]);
				 return KmerScore;
		     }
	     }
	     if(RE_KmerStr.charAt(0)=='G')
	     {
			 HasHAddress_RE=CommonClass.getHashUnit(reverse(KmerString),KmerHashTable_G,SizeOfKmerHashTable_G);
			 if(HasHAddress_RE!=-1)
		     {
				 KmerScore=(double)1/Integer.parseInt(KmerHashTable_G[HasHAddress_RE][1]);
				 return KmerScore;
		     }
	     }
	     if(RE_KmerStr.charAt(0)=='C')
	     {
			 HasHAddress_RE=CommonClass.getHashUnit(reverse(KmerString),KmerHashTable_C,SizeOfKmerHashTable_C);
			 if(HasHAddress_RE!=-1)
		     {
				 KmerScore=(double)1/Integer.parseInt(KmerHashTable_C[HasHAddress_RE][1]);
				 return KmerScore;
		     }
	     }
		 if(FR_KmerStr.charAt(0)=='A')
		 {
			 HasHAddress_FR=CommonClass.getHashUnit(KmerString,KmerHashTable_A,SizeOfKmerHashTable_A);
			 if(HasHAddress_FR!=-1)
			 {
				 KmerScore=(double)1/Integer.parseInt(KmerHashTable_A[HasHAddress_FR][1]);
				 return KmerScore;
		     }
		 }
		 if(FR_KmerStr.charAt(0)=='T')
		 {
			 HasHAddress_FR=CommonClass.getHashUnit(KmerString,KmerHashTable_T,SizeOfKmerHashTable_T);
			 if(HasHAddress_FR!=-1)
			 {
			     KmerScore=(double)1/Integer.parseInt(KmerHashTable_T[HasHAddress_FR][1]);
			     return KmerScore;
			 }
		 }
		 if(FR_KmerStr.charAt(0)=='G')
		 {
			 HasHAddress_FR=CommonClass.getHashUnit(KmerString,KmerHashTable_G,SizeOfKmerHashTable_G);
		     if(HasHAddress_FR!=-1)
			 {
				 KmerScore=(double)1/Integer.parseInt(KmerHashTable_G[HasHAddress_FR][1]);
				 return KmerScore;
			 }
		 }
		 if(FR_KmerStr.charAt(0)=='C')
		 {
		     HasHAddress_FR=CommonClass.getHashUnit(KmerString,KmerHashTable_C,SizeOfKmerHashTable_C);
			 if(HasHAddress_FR!=-1)
			 {
				 KmerScore=(double)1/Integer.parseInt(KmerHashTable_C[HasHAddress_FR][1]);
				 return KmerScore;
			 }
		 }
		 return -1;
	}
	//GetSubRepeats.
    public  static String GetSubRepeats(String str) 
    {
    	 HashMap<String, Integer> map = new HashMap<String, Integer>();
    	 for(int i=0;i<str.length()-3;i++)
    	 {  
    		 int k=i;  
    		 String str1=str.substring(i,i+2);  
    		 String str2=str.substring(i+2);       
    		 while(str2.contains(str1))
    		 {  
    		      int cnt=1;  
    		      String strtemp=str2;  
    		      while(strtemp.contains(str1))  
    		      {  
    		          cnt++;  
    		          strtemp=strtemp.substring(strtemp.indexOf(str1)+str1.length());
    		      }  
    		      if(!map.containsKey(str1))
    		      {
    		          map.put(str1,cnt);
    		      }
    		      str1=str.substring(i,++k+2);
    		      str2=str.substring(k+2);              
    		 }  
          }
    	  int CountMap=0;
          String OrderArray[]=new String[map.size()];
    	  for(Object key:map.keySet())  
    	  { 
    		 OrderArray[CountMap++]=key+":"+map.get(key);
    	  }
    	  if(CountMap>0)
    	  {
    		 String ExString="";
    		 for(int w=0;w<CountMap;w++)
    		 {
    			   String [] SplitLine1=OrderArray[w].split(":");
    			   int key1=Integer.parseInt(SplitLine1[1]);
    			   for(int r=w+1;r<CountMap;r++)
    			   {
    				    String [] SplitLine2=OrderArray[r].split(":");
    					int key2=Integer.parseInt(SplitLine2[1]);
    					if(key1<key2)
    					{
    						ExString=OrderArray[w];
    						OrderArray[w]=OrderArray[r];
    						OrderArray[r]=ExString;
    					} 
    			   }
    	      }
    		  //Output.
    		  String MaxLengthStr="";
    		  String MaxFre="";
    		  for(int w=0;w<CountMap;w++)
    		  {
    			   String [] SplitLine=OrderArray[w].split(":");
    			   if(w==0)
    			   {
    					 MaxLengthStr=SplitLine[0];
    				     MaxFre=SplitLine[1];
    			   }
    			   else if(SplitLine[0].length()>MaxLengthStr.length())
    			   {
    					 MaxLengthStr=SplitLine[0];
    					 MaxFre=SplitLine[1];
    			   }
    		   }
    		   return MaxLengthStr+":"+MaxFre;
    	  }
    	  return null;
     }
     //Computer similarity by Edit distance.
     private static int compare(String str, String target) 
     {
        int d[][];
        int n = str.length();
        int m = target.length();
        int i;
        int j;
        char ch1;
        char ch2;
        int temp;
        if (n == 0) {
                return m;
        }
        if (m == 0) {
                return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) {
                d[i][0] = i;
        }
        for (j = 0; j <= m; j++) {
                d[0][j] = j;
        }
        for (i = 1; i <= n; i++) {
                ch1 = str.charAt(i - 1);
                for (j = 1; j <= m; j++) {
                        ch2 = target.charAt(j - 1);
                        if (ch1 == ch2) {
                                temp = 0;
                        } else {
                        temp = 1;
                }
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }
    private static int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }
    public static float getSimilarityRatio(String str, String target) {
        return 1 - (float) compare(str, target) / Math.max(str.length(), target.length());
    }
    //Add common function.
}
//Format Conversion.
class FormatConversion{
	//Main.
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String Fastq_left=args[0];
		String Fastq_right=args[1];
		String FileWritePath=args[2];
		int Line_LeftFastq=CommonClass.getLineOfFastq(Fastq_left);
		int Line_RightFastq=CommonClass.getLineOfFastq(Fastq_right);
		String LeftFastqArray[]=new String[Line_LeftFastq];
		String RightFastqArray[]=new String[Line_RightFastq];
		int LineLeft=CommonClass.FastqFile_Array(Fastq_left,LeftFastqArray);
		int LineRight=CommonClass.FastqFile_Array(Fastq_right,RightFastqArray);
		//Write.
		for(int w=0;w<LineLeft;w++)
		{
			FileWriter writer1= new FileWriter(FileWritePath+"LeftFile.fa",true);
			writer1.write(LeftFastqArray[w]+"\n");
			writer1.close();
		}
		for(int r=0;r<LineRight;r++)
		{
			FileWriter writer2= new FileWriter(FileWritePath+"RightFile.fa",true);
			writer2.write(RightFastqArray[r]+"\n");
			writer2.close();
		}
		//Write PE FASTA.
		int SizeOfFQ=CommonClass.getFileLine(Fastq_left);
		String PEReadArray[]= new String[2*SizeOfFQ];
		int RealSizeOfPE=CommonClass.PEReadSetArray(Fastq_left, Fastq_right,PEReadArray);
		//Count.
		int FAlines=0;
		for(int f=0;f<RealSizeOfPE;f++)
		{
			FileWriter writer= new FileWriter(FileWritePath+"short1.fasta",true);
            writer.write(">"+(FAlines++)+"\n"+PEReadArray[f]+"\n");
            writer.close();
		}
		//Free.
		LeftFastqArray=null;
		RightFastqArray=null;
		PEReadArray=null;
	}
}
//Generate blocked KMER HashTable.
class GenerateBlockedKmerHashTable {
    //Main.
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
        String UniqueKmerPath=args[0];
        String BlockedKmerSetWritePath=args[1];
		//KmerToHashTable.
        int KmerLines=CommonClass.getLineOfKmer(UniqueKmerPath);
        String KmerArray[]=new String[KmerLines];
		int RealKmerLines=CommonClass.FileToArray(UniqueKmerPath,KmerArray);
		for(int g=0;g<RealKmerLines;g++)
		{
			if(KmerArray[g].charAt(0)=='A')
			{
				FileWriter writer= new FileWriter(BlockedKmerSetWritePath+"KmerSet_A.fa",true);
	            writer.write(KmerArray[g]+"\n");
	            writer.close();
			}
			else if(KmerArray[g].charAt(0)=='T')
			{
				FileWriter writer= new FileWriter(BlockedKmerSetWritePath+"KmerSet_T.fa",true);
	            writer.write(KmerArray[g]+"\n");
	            writer.close();
			}
			else if(KmerArray[g].charAt(0)=='G')
			{
				FileWriter writer= new FileWriter(BlockedKmerSetWritePath+"KmerSet_G.fa",true);
	            writer.write(KmerArray[g]+"\n");
	            writer.close();
			}
			else if(KmerArray[g].charAt(0)=='C')
			{
				FileWriter writer= new FileWriter(BlockedKmerSetWritePath+"KmerSet_C.fa",true);
	            writer.write(KmerArray[g]+"\n");
	            writer.close();
			}
		}
		//Free.
		KmerArray=null;
	}
}
//Generate Marginal Value Of QS.
class GetMarginalValueOfQS {
  //Main.
	public static void main(String[] args) throws IOException {	 
		 //Path.
		 String QSStatisticsWritePath=args[0];
		 String MarginalValueWritePath=args[1];
		 String FilePath=args[2];
		 //Count.
		 int SaveTempCount=0;
		 int ArraySize=CommonClass.getLineOfKmer(FilePath);
		 double SaveTempArray[][] = new double[ArraySize][2];
		 try {
			  String temp="";
			  String encoding = "utf-8";
			  File file = new File(FilePath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((temp=bufferedReader.readLine()) != null) {
                        String [] SplitLineRead = temp.split("\t|\\s+");
                        BigDecimal Db=new BigDecimal(CommonClass.GetAverageQualityScoreOfRead(SplitLineRead[2]));    
                        double f1=Db.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
                        //System.out.println("f1:"+f1);
						SaveTempArray[SaveTempCount++][0]=f1;
				  }
				  bufferedReader.close();
			  } 
			  else {
				  System.out.println("File is not exist!");
			  }
		 } catch (Exception e) {
			 System.out.println("Error liaoxingyu");
			 e.printStackTrace();
		 }
         //Filter.
         for(int f=0;f<SaveTempCount;f++)
		 {
			 if(SaveTempArray[f][0]!=0)
			 {
				 int FreCount=1;
				 for(int g=f+1;g<SaveTempCount;g++)
				 {
					 if(SaveTempArray[g][0]!=0 && SaveTempArray[f][0]==SaveTempArray[g][0])
					 {
						 FreCount++;
						 SaveTempArray[g][0]=0;
					 }
				 }
				 SaveTempArray[f][1]=FreCount;
			 }
		 }
       //Order.
	   int NotNullCount=0;
	   double NotNullSaveTempArray[][] = new double[SaveTempCount][2];
	   for(int r=0;r<SaveTempCount;r++)
	   {
		   if(SaveTempArray[r][0]!=0)
		   {
			  NotNullSaveTempArray[NotNullCount][0]=SaveTempArray[r][0];
			  NotNullSaveTempArray[NotNullCount][1]=SaveTempArray[r][1];
			  NotNullCount++;
		   }
	   }
       //Order.
	   double data1=0;
	   double data2=0;
	   for(int b=0;b<NotNullCount;b++)
	   {
		   for(int d=b+1;d<NotNullCount;d++)
		   {
			   if(NotNullSaveTempArray[b][0]>NotNullSaveTempArray[d][0])
			   {
				    data1=NotNullSaveTempArray[b][0];
				    data2=NotNullSaveTempArray[b][1];
				    NotNullSaveTempArray[b][0]=NotNullSaveTempArray[d][0];
				    NotNullSaveTempArray[b][1]=NotNullSaveTempArray[d][1];
				    NotNullSaveTempArray[d][0]=data1;
				    NotNullSaveTempArray[d][1]=data2;
		       }
		   }
	   }
	   //Write.
	   for(int q=0;q<NotNullCount;q++)
       {
		   FileWriter writer= new FileWriter(QSStatisticsWritePath,true);
           writer.write(NotNullSaveTempArray[q][0]+"\t"+NotNullSaveTempArray[q][1]+"\n");
           writer.close();
       }
	   //Get MIN.
	   double QualityScoreMin=NotNullSaveTempArray[0][0];
	   //Get Max.
	   double MaxOutKey=0;
	   double MaxOutValue=0;
	   for(int t=(int)(NotNullCount/3);t<NotNullCount;t++)
	   {
		     double CurrentKey=NotNullSaveTempArray[t][0];
			 double CurrentValue=NotNullSaveTempArray[t][1];
			 if(t==(int)(NotNullCount/3))
			 {
				 MaxOutKey=CurrentKey;
				 MaxOutValue=CurrentValue;
			 }
			 else if(CurrentValue>MaxOutValue)
			 {
				 MaxOutKey=CurrentKey;
				 MaxOutValue=CurrentValue;
			 }
	   }
	   //Save.
	   FileWriter writer= new FileWriter(MarginalValueWritePath,true);
	   writer.write(MaxOutKey+"\t"+QualityScoreMin);
	   writer.close();
	   //Free.
	   SaveTempArray=null;
	   NotNullSaveTempArray=null;
	}
}
//Trim read.
class peTrimer {
	//Main.
	public static void main(String[] args) throws IOException, Exception, ExecutionException {
		//TODO Auto-generated method stub
		String UniqueKmerPath=args[0];
		String FilterThreshold=args[1];
		String WindowSize=args[2];
		String ReadLength=args[3];
		String FilePath=args[4];
		String MarginalValuePath=args[5];
		String AdapterStringPath=args[6];
		String TrimInformationsWritePath=args[7];
		//Get filter threshold
		double filterThreshold=Double.parseDouble(FilterThreshold);
		//Get Threshold.
        String MarginalValue="";
        String encoding="utf-8";
		String readtemp ="";
        File file = new File(MarginalValuePath);
    	if (file.isFile() && file.exists()) {
    	    InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
    		BufferedReader bufferedReader = new BufferedReader(read);
    		while ((readtemp = bufferedReader.readLine()) != null) 
    	    {
		         MarginalValue=readtemp;
			     break;
			}
    		bufferedReader.close();
		}
		String [] SplitLine = MarginalValue.split("\t|\\s+");
		double MaxQualityScore=Double.parseDouble(SplitLine[0]);
        double MinQualityScore=Double.parseDouble(SplitLine[1]);
        double FilterThre=filterThreshold*(MaxQualityScore-MinQualityScore)+MinQualityScore;
        System.out.println("MaxQualityScore:"+MaxQualityScore+"\t"+"MinQualityScore:"+MinQualityScore+"\t"+"FilterThre:"+FilterThre);
		//Loading data.
		int FileSize=CommonClass.getFileLine(FilePath);
        String FileArray[]=new String[FileSize];
		int RealSizeArray=CommonClass.FileToArray(FilePath,FileArray);
		System.out.println("RealSizeArray:"+RealSizeArray);
		//Array.
		int SizeOfHash_A=CommonClass.getLineOfKmer(UniqueKmerPath+"KmerSet_A.fa")+100;
		String KmerHashTable_A[][]=new String[SizeOfHash_A][2];
		int SizeOfHash_T=CommonClass.getLineOfKmer(UniqueKmerPath+"KmerSet_T.fa")+100;
		String KmerHashTable_T[][]=new String[SizeOfHash_T][2];
		int SizeOfHash_G=CommonClass.getLineOfKmer(UniqueKmerPath+"KmerSet_G.fa")+100;
		String KmerHashTable_G[][]=new String[SizeOfHash_G][2];
		int SizeOfHash_C=CommonClass.getLineOfKmer(UniqueKmerPath+"KmerSet_C.fa")+100;
		String KmerHashTable_C[][]=new String[SizeOfHash_C][2];
		//initialization
		for(int t=0;t<SizeOfHash_A;t++)
	    {
			KmerHashTable_A[t][0]=null;
			KmerHashTable_A[t][1]=null;
		}
		for(int t=0;t<SizeOfHash_T;t++)
	    {
			KmerHashTable_T[t][0]=null;
			KmerHashTable_T[t][1]=null;
		}
		for(int t=0;t<SizeOfHash_G;t++)
	    {
			KmerHashTable_G[t][0]=null;
			KmerHashTable_G[t][1]=null;
		}
		for(int t=0;t<SizeOfHash_C;t++)
	    {
			KmerHashTable_C[t][0]=null;
			KmerHashTable_C[t][1]=null;
		}
	    CommonClass.KmerFileToHash(UniqueKmerPath+"KmerSet_A.fa",KmerHashTable_A,SizeOfHash_A);
	    CommonClass.KmerFileToHash(UniqueKmerPath+"KmerSet_T.fa",KmerHashTable_T,SizeOfHash_T);
	    CommonClass.KmerFileToHash(UniqueKmerPath+"KmerSet_G.fa",KmerHashTable_G,SizeOfHash_G);
	    CommonClass.KmerFileToHash(UniqueKmerPath+"KmerSet_C.fa",KmerHashTable_C,SizeOfHash_C);
		//Replace.
		String Situation1="";
		String Situation2="";
		String Situation3="";
		String Situation4="";
		String Situation5="";
        String RepStr="";
        String CheckStr="";
		for(int u=0;u<ReadLength.length();u++)
		{
			if(u==0)
			{
				Situation1="N";
				Situation2="A";
				Situation3="C";
				Situation4="G";
				Situation5="T";
				CheckStr="#";
			}
			else
			{
				Situation1+="N";
				Situation2+="A";
				Situation3+="C";
				Situation4+="G";
				Situation5+="T";
				CheckStr+="#";
			}
		}
        //Merge.
		RepStr=Situation1+"\t"+Situation2+"\t"+Situation3+"\t"+Situation4+"\t"+Situation5;
		//GetAdapter.
		int AdapterFlag=1;
		int LineOfAdapterFile=CommonClass.getFileLine(AdapterStringPath);
		int AdapterCount=0;
		String AdapterArray[]=new String[LineOfAdapterFile];
		String TempString="";
		File AdapterFile = new File(AdapterStringPath);
		if (AdapterFile.isFile() && AdapterFile.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(AdapterFile), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((TempString=bufferedReader.readLine()) != null) {
				  AdapterArray[AdapterCount++]=TempString;
			}
			bufferedReader.close();
		}
		else
		{
			AdapterFlag=0;
		}
        //Multiple Threads.
      	ExecutorService pool_1 = Executors.newFixedThreadPool(48);
      	int scount=RealSizeArray;
      	int SplitSize = RealSizeArray/48;
      	int Readlength=Integer.parseInt(ReadLength);
      	int windowSize=Integer.parseInt(WindowSize); 
      	@SuppressWarnings("rawtypes")
      	Callable c1_1=new RemoveTechSeqByMultipleThreads(0,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,0,SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_2=new RemoveTechSeqByMultipleThreads(1,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,SplitSize,2*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_3=new RemoveTechSeqByMultipleThreads(2,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,2*SplitSize,3*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_4=new RemoveTechSeqByMultipleThreads(3,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,3*SplitSize,4*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_5=new RemoveTechSeqByMultipleThreads(4,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,4*SplitSize,5*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_6=new RemoveTechSeqByMultipleThreads(5,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,5*SplitSize,6*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_7=new RemoveTechSeqByMultipleThreads(6,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,6*SplitSize,7*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_8=new RemoveTechSeqByMultipleThreads(7,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,7*SplitSize,8*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_9=new RemoveTechSeqByMultipleThreads(8,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,8*SplitSize,9*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_10=new RemoveTechSeqByMultipleThreads(9,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,9*SplitSize,10*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);	
      	@SuppressWarnings("rawtypes")
      	Callable c1_11=new RemoveTechSeqByMultipleThreads(10,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,10*SplitSize,11*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_12=new RemoveTechSeqByMultipleThreads(11,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,11*SplitSize,12*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_13=new RemoveTechSeqByMultipleThreads(12,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,12*SplitSize,13*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_14=new RemoveTechSeqByMultipleThreads(13,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,13*SplitSize,14*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_15=new RemoveTechSeqByMultipleThreads(14,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,14*SplitSize,15*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_16=new RemoveTechSeqByMultipleThreads(15,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,15*SplitSize,16*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_17=new RemoveTechSeqByMultipleThreads(16,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,16*SplitSize,17*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_18=new RemoveTechSeqByMultipleThreads(17,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,17*SplitSize,18*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_19=new RemoveTechSeqByMultipleThreads(18,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,18*SplitSize,19*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_20=new RemoveTechSeqByMultipleThreads(19,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,19*SplitSize,20*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_21=new RemoveTechSeqByMultipleThreads(20,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,20*SplitSize,21*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_22=new RemoveTechSeqByMultipleThreads(21,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,21*SplitSize,22*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_23=new RemoveTechSeqByMultipleThreads(22,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,22*SplitSize,23*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);	
      	@SuppressWarnings("rawtypes")
      	Callable c1_24=new RemoveTechSeqByMultipleThreads(23,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,23*SplitSize,24*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_25=new RemoveTechSeqByMultipleThreads(24,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,24*SplitSize,25*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_26=new RemoveTechSeqByMultipleThreads(25,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,25*SplitSize,26*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_27=new RemoveTechSeqByMultipleThreads(26,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,26*SplitSize,27*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_28=new RemoveTechSeqByMultipleThreads(27,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,27*SplitSize,28*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_29=new RemoveTechSeqByMultipleThreads(28,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,28*SplitSize,29*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_30=new RemoveTechSeqByMultipleThreads(29,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,29*SplitSize,30*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_31=new RemoveTechSeqByMultipleThreads(30,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,30*SplitSize,31*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_32=new RemoveTechSeqByMultipleThreads(31,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,31*SplitSize,32*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);	
      	@SuppressWarnings("rawtypes")
      	Callable c1_33=new RemoveTechSeqByMultipleThreads(32,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,32*SplitSize,33*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_34=new RemoveTechSeqByMultipleThreads(33,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,33*SplitSize,34*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_35=new RemoveTechSeqByMultipleThreads(34,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,34*SplitSize,35*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_36=new RemoveTechSeqByMultipleThreads(35,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,35*SplitSize,36*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_37=new RemoveTechSeqByMultipleThreads(36,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,36*SplitSize,37*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_38=new RemoveTechSeqByMultipleThreads(37,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,37*SplitSize,38*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_39=new RemoveTechSeqByMultipleThreads(38,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,38*SplitSize,39*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_40=new RemoveTechSeqByMultipleThreads(39,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,39*SplitSize,40*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_41=new RemoveTechSeqByMultipleThreads(40,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,40*SplitSize,41*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_42=new RemoveTechSeqByMultipleThreads(41,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,41*SplitSize,42*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_43=new RemoveTechSeqByMultipleThreads(42,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,42*SplitSize,43*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);	
      	@SuppressWarnings("rawtypes")
      	Callable c1_44=new RemoveTechSeqByMultipleThreads(43,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,43*SplitSize,44*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_45=new RemoveTechSeqByMultipleThreads(44,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,44*SplitSize,45*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
     	Callable c1_46=new RemoveTechSeqByMultipleThreads(45,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,45*SplitSize,46*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_47=new RemoveTechSeqByMultipleThreads(46,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,46*SplitSize,47*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c1_48=new RemoveTechSeqByMultipleThreads(47,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,47*SplitSize,scount-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);	
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_1 = pool_1.submit(c1_1);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f1_2 = pool_1.submit(c1_2);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_3 = pool_1.submit(c1_3);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_4 = pool_1.submit(c1_4);
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_5 = pool_1.submit(c1_5);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_6 = pool_1.submit(c1_6);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_7 = pool_1.submit(c1_7);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_8 = pool_1.submit(c1_8);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_9 = pool_1.submit(c1_9);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_10 = pool_1.submit(c1_10);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_11 = pool_1.submit(c1_11);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f1_12 = pool_1.submit(c1_12);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_13 = pool_1.submit(c1_13);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_14 = pool_1.submit(c1_14);
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_15 = pool_1.submit(c1_15);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_16 = pool_1.submit(c1_16);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_17 = pool_1.submit(c1_17);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_18 = pool_1.submit(c1_18);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_19 = pool_1.submit(c1_19);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_20 = pool_1.submit(c1_20);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_21 = pool_1.submit(c1_21);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f1_22 = pool_1.submit(c1_22);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_23 = pool_1.submit(c1_23);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_24 = pool_1.submit(c1_24);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_25 = pool_1.submit(c1_25);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_26 = pool_1.submit(c1_26);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_27 = pool_1.submit(c1_27);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_28 = pool_1.submit(c1_28);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_29 = pool_1.submit(c1_29);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_30 = pool_1.submit(c1_30);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_31 = pool_1.submit(c1_31);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f1_32 = pool_1.submit(c1_32);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_33 = pool_1.submit(c1_33);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_34 = pool_1.submit(c1_34);
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_35 = pool_1.submit(c1_35);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_36 = pool_1.submit(c1_36);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_37 = pool_1.submit(c1_37);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_38 = pool_1.submit(c1_38);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_39 = pool_1.submit(c1_39);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_40 = pool_1.submit(c1_40);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_41 = pool_1.submit(c1_41);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f1_42 = pool_1.submit(c1_42);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_43 = pool_1.submit(c1_43);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_44 = pool_1.submit(c1_44);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_45 = pool_1.submit(c1_45);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_46 = pool_1.submit(c1_46);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_47 = pool_1.submit(c1_47);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_48 = pool_1.submit(c1_48);
		// Future
      	if(f1_1.get().toString()!=null)
      	{
      		System.out.println(f1_1.get().toString());
      	}
      	if(f1_2.get().toString()!=null)
      	{
      		System.out.println(f1_2.get().toString());
      	}
      	if(f1_3.get().toString()!=null)
      	{
      		System.out.println(f1_3.get().toString());
        }
      	if(f1_4.get().toString()!=null)
      	{
      	    System.out.println(f1_4.get().toString());
      	}
      	if(f1_5.get().toString()!=null)
      	{
      	    System.out.println(f1_5.get().toString());
      	}
      	if(f1_6.get().toString()!=null)
      	{
      	    System.out.println(f1_6.get().toString());
      	}
      	if(f1_7.get().toString()!=null)
      	{
      		System.out.println(f1_7.get().toString());
      	}
      	if(f1_8.get().toString()!=null)
      	{
      		System.out.println(f1_8.get().toString());
      	}
      	if(f1_9.get().toString()!=null)
      	{
      	    System.out.println(f1_9.get().toString());
      	}
      	if(f1_10.get().toString()!=null)
      	{
      		System.out.println(f1_10.get().toString());
      	}
      	if(f1_11.get().toString()!=null)
      	{
      		System.out.println(f1_11.get().toString());
      	}
      	if(f1_12.get().toString()!=null)
      	{
      		System.out.println(f1_12.get().toString());
      	}
      	if(f1_13.get().toString()!=null)
      	{
      		System.out.println(f1_13.get().toString());
        }
      	if(f1_14.get().toString()!=null)
      	{
      	    System.out.println(f1_14.get().toString());
      	}
      	if(f1_15.get().toString()!=null)
        {
      		System.out.println(f1_15.get().toString());
      	}
      	if(f1_16.get().toString()!=null)
      	{
      	    System.out.println(f1_16.get().toString());
      	}
      	if(f1_17.get().toString()!=null)
      	{
      		System.out.println(f1_17.get().toString());
      	}
      	if(f1_18.get().toString()!=null)
      	{
      		System.out.println(f1_18.get().toString());
      	}
      	if(f1_19.get().toString()!=null)
      	{
      		System.out.println(f1_19.get().toString());
        }
      	if(f1_20.get().toString()!=null)
      	{
      		System.out.println(f1_20.get().toString());
      	}
        if(f1_21.get().toString()!=null)
      	{
      		System.out.println(f1_21.get().toString());
        }
      	if(f1_22.get().toString()!=null)
      	{
      		System.out.println(f1_22.get().toString());
      	}
      	if(f1_23.get().toString()!=null)
      	{
      		System.out.println(f1_23.get().toString());
        }
      	if(f1_24.get().toString()!=null)
      	{
      	    System.out.println(f1_24.get().toString());
      	}
      	if(f1_25.get().toString()!=null)
      	{
      		System.out.println(f1_25.get().toString());
      	}
      	if(f1_26.get().toString()!=null)
      	{
      		 System.out.println(f1_26.get().toString());
        }
      	if(f1_27.get().toString()!=null)
      	{
      		 System.out.println(f1_27.get().toString());
      	}
      	if(f1_28.get().toString()!=null)
      	{
      		 System.out.println(f1_28.get().toString());
      	}
      	if(f1_29.get().toString()!=null)
      	{
      		 System.out.println(f1_29.get().toString());
      	}
      	if(f1_30.get().toString()!=null)
      	{
      		 System.out.println(f1_30.get().toString());
        }
      	if(f1_31.get().toString()!=null)
      	{
      		 System.out.println(f1_31.get().toString());
      	}
        if(f1_32.get().toString()!=null)
      	{
      		 System.out.println(f1_32.get().toString());
      	}
        if(f1_33.get().toString()!=null)
      	{
      		 System.out.println(f1_33.get().toString());
      	}
      	if(f1_34.get().toString()!=null)
      	{
      	     System.out.println(f1_34.get().toString());
      	}
      	if(f1_35.get().toString()!=null)
      	{
      		 System.out.println(f1_35.get().toString());
      	}
      	if(f1_36.get().toString()!=null)
      	{
      		 System.out.println(f1_36.get().toString());
      	}
        if(f1_37.get().toString()!=null)
      	{
      		 System.out.println(f1_37.get().toString());
      	}
      	if(f1_38.get().toString()!=null)
      	{
      		 System.out.println(f1_38.get().toString());
      	}
      	if(f1_39.get().toString()!=null)
        {
      		 System.out.println(f1_39.get().toString());
      	}
      	if(f1_40.get().toString()!=null)
      	{
      		 System.out.println(f1_40.get().toString());
      	}
        if(f1_41.get().toString()!=null)
      	{
      		 System.out.println(f1_41.get().toString());
        }
        if(f1_42.get().toString()!=null)
      	{
      		 System.out.println(f1_42.get().toString());
      	}
        if(f1_43.get().toString()!=null)
      	{
      		 System.out.println(f1_43.get().toString());
        }
      	if(f1_44.get().toString()!=null)
      	{
      	     System.out.println(f1_44.get().toString());
      	}
      	if(f1_45.get().toString()!=null)
      	{
      	     System.out.println(f1_45.get().toString());
      	}
      	if(f1_46.get().toString()!=null)
      	{
      		 System.out.println(f1_46.get().toString());
      	}
        if(f1_47.get().toString()!=null)
      	{
      		 System.out.println(f1_47.get().toString());
      	}
      	if(f1_48.get().toString()!=null)
      	{
      		System.out.println(f1_48.get().toString());
      	}
      	System.out.println("Trimming completed!");
      	//Write.
      	for(int v=0;v<RealSizeArray;v++)
      	{
			FileWriter writer= new FileWriter(TrimInformationsWritePath,true);
            writer.write(FileArray[v]+"\n");
            writer.close();
      	}
      	//shutdown pool.
      	pool_1.shutdown();   
    }
}

//Trim read.
class TrimerPairedReads {
	//Main.
	public static void main(String[] args) throws IOException, Exception, ExecutionException {
		//TODO Auto-generated method stub
		String UniqueKmerPath=args[0];
		String FilterThreshold=args[1];
		String WindowSize=args[2];
		String ReadLength=args[3];
		String FilePath=args[4];
		String MarginalValuePath=args[5];
		String AdapterStringPath=args[6];
		String TrimInformationsWritePath=args[7];
		//Get filter threshold
		double filterThreshold=Double.parseDouble(FilterThreshold);
		//Get Threshold.
        String MarginalValue="";
        String encoding="utf-8";
		String readtemp ="";
        File file = new File(MarginalValuePath);
    	if (file.isFile() && file.exists()) {
    	    InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
    		BufferedReader bufferedReader = new BufferedReader(read);
    		while ((readtemp = bufferedReader.readLine()) != null) 
    	    {
		         MarginalValue=readtemp;
			     break;
			}
    		bufferedReader.close();
		}
		String [] SplitLine = MarginalValue.split("\t|\\s+");
		double MaxQualityScore=Double.parseDouble(SplitLine[0]);
        double MinQualityScore=Double.parseDouble(SplitLine[1]);
        double FilterThre=filterThreshold*(MaxQualityScore-MinQualityScore)+MinQualityScore;
        System.out.println("MaxQualityScore:"+MaxQualityScore+"\t"+"MinQualityScore:"+MinQualityScore+"\t"+"FilterThre:"+FilterThre);
		//Loading data.
		int FileSize=CommonClass.getFileLine(FilePath);
        String FileArray[]=new String[FileSize];
		int RealSizeArray=CommonClass.FileToArray(FilePath,FileArray);
		System.out.println("RealSizeArray:"+RealSizeArray);
		//Array.
		int SizeOfHash_A=CommonClass.getLineOfKmer(UniqueKmerPath+"KmerSet_A.fa")+100;
		String KmerHashTable_A[][]=new String[SizeOfHash_A][2];
		int SizeOfHash_T=CommonClass.getLineOfKmer(UniqueKmerPath+"KmerSet_T.fa")+100;
		String KmerHashTable_T[][]=new String[SizeOfHash_T][2];
		int SizeOfHash_G=CommonClass.getLineOfKmer(UniqueKmerPath+"KmerSet_G.fa")+100;
		String KmerHashTable_G[][]=new String[SizeOfHash_G][2];
		int SizeOfHash_C=CommonClass.getLineOfKmer(UniqueKmerPath+"KmerSet_C.fa")+100;
		String KmerHashTable_C[][]=new String[SizeOfHash_C][2];
		//initialization
		for(int t=0;t<SizeOfHash_A;t++)
	    {
			KmerHashTable_A[t][0]=null;
			KmerHashTable_A[t][1]=null;
		}
		for(int t=0;t<SizeOfHash_T;t++)
	    {
			KmerHashTable_T[t][0]=null;
			KmerHashTable_T[t][1]=null;
		}
		for(int t=0;t<SizeOfHash_G;t++)
	    {
			KmerHashTable_G[t][0]=null;
			KmerHashTable_G[t][1]=null;
		}
		for(int t=0;t<SizeOfHash_C;t++)
	    {
			KmerHashTable_C[t][0]=null;
			KmerHashTable_C[t][1]=null;
		}
	    CommonClass.KmerFileToHash(UniqueKmerPath+"KmerSet_A.fa",KmerHashTable_A,SizeOfHash_A);
	    CommonClass.KmerFileToHash(UniqueKmerPath+"KmerSet_T.fa",KmerHashTable_T,SizeOfHash_T);
	    CommonClass.KmerFileToHash(UniqueKmerPath+"KmerSet_G.fa",KmerHashTable_G,SizeOfHash_G);
	    CommonClass.KmerFileToHash(UniqueKmerPath+"KmerSet_C.fa",KmerHashTable_C,SizeOfHash_C);
		//Replace.
		String Situation1="";
		String Situation2="";
		String Situation3="";
		String Situation4="";
		String Situation5="";
        String RepStr="";
        String CheckStr="";
		for(int u=0;u<ReadLength.length();u++)
		{
			if(u==0)
			{
				Situation1="N";
				Situation2="A";
				Situation3="C";
				Situation4="G";
				Situation5="T";
				CheckStr="#";
			}
			else
			{
				Situation1+="N";
				Situation2+="A";
				Situation3+="C";
				Situation4+="G";
				Situation5+="T";
				CheckStr+="#";
			}
		}
        //Merge.
		RepStr=Situation1+"\t"+Situation2+"\t"+Situation3+"\t"+Situation4+"\t"+Situation5;
		//GetAdapter.
		int AdapterFlag=1;
		int LineOfAdapterFile=CommonClass.getFileLine(AdapterStringPath);
		int AdapterCount=0;
		String AdapterArray[]=new String[LineOfAdapterFile];
		String TempString="";
		File AdapterFile = new File(AdapterStringPath);
		if (AdapterFile.isFile() && AdapterFile.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(AdapterFile), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((TempString=bufferedReader.readLine()) != null) {
				  AdapterArray[AdapterCount++]=TempString;
			}
			bufferedReader.close();
		}
		else
		{
			AdapterFlag=0;
		}
        //Multiple Threads.
      	ExecutorService pool_2 = Executors.newFixedThreadPool(48);
      	int scount=RealSizeArray;
      	int SplitSize = RealSizeArray/48;
      	int Readlength=Integer.parseInt(ReadLength);
      	int windowSize=Integer.parseInt(WindowSize); 
      	@SuppressWarnings("rawtypes")
      	Callable c2_1=new TrimByMultipleThreads(0,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,0,SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_2=new TrimByMultipleThreads(1,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,SplitSize,2*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_3=new TrimByMultipleThreads(2,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,2*SplitSize,3*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_4=new TrimByMultipleThreads(3,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,3*SplitSize,4*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_5=new TrimByMultipleThreads(4,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,4*SplitSize,5*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_6=new TrimByMultipleThreads(5,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,5*SplitSize,6*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_7=new TrimByMultipleThreads(6,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,6*SplitSize,7*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_8=new TrimByMultipleThreads(7,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,7*SplitSize,8*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_9=new TrimByMultipleThreads(8,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,8*SplitSize,9*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_10=new TrimByMultipleThreads(9,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,9*SplitSize,10*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);	
      	@SuppressWarnings("rawtypes")
      	Callable c2_11=new TrimByMultipleThreads(10,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,10*SplitSize,11*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_12=new TrimByMultipleThreads(11,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,11*SplitSize,12*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_13=new TrimByMultipleThreads(12,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,12*SplitSize,13*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_14=new TrimByMultipleThreads(13,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,13*SplitSize,14*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_15=new TrimByMultipleThreads(14,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,14*SplitSize,15*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_16=new TrimByMultipleThreads(15,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,15*SplitSize,16*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_17=new TrimByMultipleThreads(16,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,16*SplitSize,17*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_18=new TrimByMultipleThreads(17,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,17*SplitSize,18*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_19=new TrimByMultipleThreads(18,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,18*SplitSize,19*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_20=new TrimByMultipleThreads(19,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,19*SplitSize,20*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_21=new TrimByMultipleThreads(20,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,20*SplitSize,21*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_22=new TrimByMultipleThreads(21,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,21*SplitSize,22*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_23=new TrimByMultipleThreads(22,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,22*SplitSize,23*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);	
      	@SuppressWarnings("rawtypes")
      	Callable c2_24=new TrimByMultipleThreads(23,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,23*SplitSize,24*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_25=new TrimByMultipleThreads(24,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,24*SplitSize,25*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_26=new TrimByMultipleThreads(25,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,25*SplitSize,26*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_27=new TrimByMultipleThreads(26,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,26*SplitSize,27*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_28=new TrimByMultipleThreads(27,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,27*SplitSize,28*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_29=new TrimByMultipleThreads(28,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,28*SplitSize,29*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_30=new TrimByMultipleThreads(29,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,29*SplitSize,30*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_31=new TrimByMultipleThreads(30,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,30*SplitSize,31*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_32=new TrimByMultipleThreads(31,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,31*SplitSize,32*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);	
      	@SuppressWarnings("rawtypes")
      	Callable c2_33=new TrimByMultipleThreads(32,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,32*SplitSize,33*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_34=new TrimByMultipleThreads(33,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,33*SplitSize,34*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_35=new TrimByMultipleThreads(34,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,34*SplitSize,35*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_36=new TrimByMultipleThreads(35,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,35*SplitSize,36*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_37=new TrimByMultipleThreads(36,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,36*SplitSize,37*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_38=new TrimByMultipleThreads(37,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,37*SplitSize,38*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_39=new TrimByMultipleThreads(38,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,38*SplitSize,39*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_40=new TrimByMultipleThreads(39,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,39*SplitSize,40*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_41=new TrimByMultipleThreads(40,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,40*SplitSize,41*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_42=new TrimByMultipleThreads(41,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,41*SplitSize,42*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_43=new TrimByMultipleThreads(42,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,42*SplitSize,43*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);	
      	@SuppressWarnings("rawtypes")
      	Callable c2_44=new TrimByMultipleThreads(43,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,43*SplitSize,44*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_45=new TrimByMultipleThreads(44,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,44*SplitSize,45*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
     	Callable c2_46=new TrimByMultipleThreads(45,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,45*SplitSize,46*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_47=new TrimByMultipleThreads(46,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,46*SplitSize,47*SplitSize-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);
      	@SuppressWarnings("rawtypes")
      	Callable c2_48=new TrimByMultipleThreads(47,Readlength,FilterThre,RepStr,CheckStr,MinQualityScore,windowSize,AdapterFlag,AdapterCount,AdapterArray,scount,FileArray,SplitSize,47*SplitSize,scount-1,KmerHashTable_A,SizeOfHash_A,KmerHashTable_T,SizeOfHash_T,KmerHashTable_G,SizeOfHash_G,KmerHashTable_C,SizeOfHash_C);	
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_1 = pool_2.submit(c2_1);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f2_2 = pool_2.submit(c2_2);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_3 = pool_2.submit(c2_3);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_4 = pool_2.submit(c2_4);
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_5 = pool_2.submit(c2_5);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_6 = pool_2.submit(c2_6);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_7 = pool_2.submit(c2_7);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_8 = pool_2.submit(c2_8);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_9 = pool_2.submit(c2_9);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_10 = pool_2.submit(c2_10);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_11 = pool_2.submit(c2_11);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f2_12 = pool_2.submit(c2_12);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_13 = pool_2.submit(c2_13);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_14 = pool_2.submit(c2_14);
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_15 = pool_2.submit(c2_15);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_16 = pool_2.submit(c2_16);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_17 = pool_2.submit(c2_17);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_18 = pool_2.submit(c2_18);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_19 = pool_2.submit(c2_19);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_20 = pool_2.submit(c2_20);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_21 = pool_2.submit(c2_21);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f2_22 = pool_2.submit(c2_22);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_23 = pool_2.submit(c2_23);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_24 = pool_2.submit(c2_24);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_25 = pool_2.submit(c2_25);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_26 = pool_2.submit(c2_26);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_27 = pool_2.submit(c2_27);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_28 = pool_2.submit(c2_28);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_29 = pool_2.submit(c2_29);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_30 = pool_2.submit(c2_30);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_31 = pool_2.submit(c2_31);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f2_32 = pool_2.submit(c2_32);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_33 = pool_2.submit(c2_33);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_34 = pool_2.submit(c2_34);
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_35 = pool_2.submit(c2_35);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_36 = pool_2.submit(c2_36);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_37 = pool_2.submit(c2_37);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_38 = pool_2.submit(c2_38);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_39 = pool_2.submit(c2_39);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_40 = pool_2.submit(c2_40);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_41 = pool_2.submit(c2_41);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f2_42 = pool_2.submit(c2_42);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_43 = pool_2.submit(c2_43);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_44 = pool_2.submit(c2_44);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_45 = pool_2.submit(c2_45);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_46 = pool_2.submit(c2_46);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_47 = pool_2.submit(c2_47);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f2_48 = pool_2.submit(c2_48);
		// Future
      	if(f2_1.get().toString()!=null)
      	{
      		System.out.println(f2_1.get().toString());
      	}
      	if(f2_2.get().toString()!=null)
      	{
      		System.out.println(f2_2.get().toString());
      	}
      	if(f2_3.get().toString()!=null)
      	{
      		System.out.println(f2_3.get().toString());
        }
      	if(f2_4.get().toString()!=null)
      	{
      	    System.out.println(f2_4.get().toString());
      	}
      	if(f2_5.get().toString()!=null)
      	{
      	    System.out.println(f2_5.get().toString());
      	}
      	if(f2_6.get().toString()!=null)
      	{
      	    System.out.println(f2_6.get().toString());
      	}
      	if(f2_7.get().toString()!=null)
      	{
      		System.out.println(f2_7.get().toString());
      	}
      	if(f2_8.get().toString()!=null)
      	{
      		System.out.println(f2_8.get().toString());
      	}
      	if(f2_9.get().toString()!=null)
      	{
      	    System.out.println(f2_9.get().toString());
      	}
      	if(f2_10.get().toString()!=null)
      	{
      		System.out.println(f2_10.get().toString());
      	}
      	if(f2_11.get().toString()!=null)
      	{
      		System.out.println(f2_11.get().toString());
      	}
      	if(f2_12.get().toString()!=null)
      	{
      		System.out.println(f2_12.get().toString());
      	}
      	if(f2_13.get().toString()!=null)
      	{
      		System.out.println(f2_13.get().toString());
        }
      	if(f2_14.get().toString()!=null)
      	{
      	    System.out.println(f2_14.get().toString());
      	}
      	if(f2_15.get().toString()!=null)
        {
      		System.out.println(f2_15.get().toString());
      	}
      	if(f2_16.get().toString()!=null)
      	{
      	    System.out.println(f2_16.get().toString());
      	}
      	if(f2_17.get().toString()!=null)
      	{
      		System.out.println(f2_17.get().toString());
      	}
      	if(f2_18.get().toString()!=null)
      	{
      		System.out.println(f2_18.get().toString());
      	}
      	if(f2_19.get().toString()!=null)
      	{
      		System.out.println(f2_19.get().toString());
        }
      	if(f2_20.get().toString()!=null)
      	{
      		System.out.println(f2_20.get().toString());
      	}
        if(f2_21.get().toString()!=null)
      	{
      		System.out.println(f2_21.get().toString());
        }
      	if(f2_22.get().toString()!=null)
      	{
      		System.out.println(f2_22.get().toString());
      	}
      	if(f2_23.get().toString()!=null)
      	{
      		System.out.println(f2_23.get().toString());
        }
      	if(f2_24.get().toString()!=null)
      	{
      	    System.out.println(f2_24.get().toString());
      	}
      	if(f2_25.get().toString()!=null)
      	{
      		System.out.println(f2_25.get().toString());
      	}
      	if(f2_26.get().toString()!=null)
      	{
      		 System.out.println(f2_26.get().toString());
        }
      	if(f2_27.get().toString()!=null)
      	{
      		 System.out.println(f2_27.get().toString());
      	}
      	if(f2_28.get().toString()!=null)
      	{
      		 System.out.println(f2_28.get().toString());
      	}
      	if(f2_29.get().toString()!=null)
      	{
      		 System.out.println(f2_29.get().toString());
      	}
      	if(f2_30.get().toString()!=null)
      	{
      		 System.out.println(f2_30.get().toString());
        }
      	if(f2_31.get().toString()!=null)
      	{
      		 System.out.println(f2_31.get().toString());
      	}
        if(f2_32.get().toString()!=null)
      	{
      		 System.out.println(f2_32.get().toString());
      	}
        if(f2_33.get().toString()!=null)
      	{
      		 System.out.println(f2_33.get().toString());
      	}
      	if(f2_34.get().toString()!=null)
      	{
      	     System.out.println(f2_34.get().toString());
      	}
      	if(f2_35.get().toString()!=null)
      	{
      		 System.out.println(f2_35.get().toString());
      	}
      	if(f2_36.get().toString()!=null)
      	{
      		 System.out.println(f2_36.get().toString());
      	}
        if(f2_37.get().toString()!=null)
      	{
      		 System.out.println(f2_37.get().toString());
      	}
      	if(f2_38.get().toString()!=null)
      	{
      		 System.out.println(f2_38.get().toString());
      	}
      	if(f2_39.get().toString()!=null)
        {
      		 System.out.println(f2_39.get().toString());
      	}
      	if(f2_40.get().toString()!=null)
      	{
      		 System.out.println(f2_40.get().toString());
      	}
        if(f2_41.get().toString()!=null)
      	{
      		 System.out.println(f2_41.get().toString());
        }
        if(f2_42.get().toString()!=null)
      	{
      		 System.out.println(f2_42.get().toString());
      	}
        if(f2_43.get().toString()!=null)
      	{
      		 System.out.println(f2_43.get().toString());
        }
      	if(f2_44.get().toString()!=null)
      	{
      	     System.out.println(f2_44.get().toString());
      	}
      	if(f2_45.get().toString()!=null)
      	{
      	     System.out.println(f2_45.get().toString());
      	}
      	if(f2_46.get().toString()!=null)
      	{
      		 System.out.println(f2_46.get().toString());
      	}
        if(f2_47.get().toString()!=null)
      	{
      		 System.out.println(f2_47.get().toString());
      	}
      	if(f2_48.get().toString()!=null)
      	{
      		System.out.println(f2_48.get().toString());
      	}
      	System.out.println("Trimming completed!");
      	//Write.
      	int LineNum=0;
      	for(int v=0;v<RealSizeArray;v++)
      	{
			FileWriter writer= new FileWriter(TrimInformationsWritePath,true);
            writer.write(">"+(LineNum++)+":"+FileArray[v].length()+"\n"+FileArray[v]+"\n");
            writer.close();
      	}
      	//shutdown pool.
      	pool_2.shutdown();   
    }
}

//MarkReads.
class RemoveTechSeqByMultipleThreads implements Callable<Object>{
	
	int Index=0;
	int Start;
	int end;
	int TrimRate=0;
	int RemoveRate=0;
	int ArraySize=0;
	int SizeOfHashTable_A=0;
	int SizeOfHashTable_T=0;
	int SizeOfHashTable_G=0;
	int SizeOfHashTable_C=0;
	int readlength=0;
	int windowsize=0;
	int SplitSize=0;
	int AdapterFlag=0;
	int SizeAdapterArray=0;
	double Threshold=0;
	double MinSQ=0;
	String AdapterArray[];
	String ReadSetArray[];
	String KmerFreHashTable_A[][];
	String KmerFreHashTable_T[][];
	String KmerFreHashTable_G[][];
	String KmerFreHashTable_C[][];
	String encoding="utf-8";
	String ReplaceStr="";
	String CheckStr="";
	//Construction.
	public RemoveTechSeqByMultipleThreads(int index,int Readlength,double threshold,String replaceStr,String checkStr,double minSQ,int Windowsize,int adapterFlag,int sizeAdapterArray,String adapterArray[],int scount,String readSetArray[],int splitSize,int StartPosition,int Endposition,String kmerFreHashTable_A[][],int sizeofHashTable_A,String kmerFreHashTable_T[][],int sizeofHashTable_T,String kmerFreHashTable_G[][],int sizeofHashTable_G,String kmerFreHashTable_C[][],int sizeofHashTable_C)
	{
		Start=StartPosition;
		end=Endposition;
	    readlength=Readlength;
		ReadSetArray=readSetArray;
		Threshold=threshold;
		MinSQ=minSQ;
		ReplaceStr=replaceStr;
		CheckStr=checkStr;
		KmerFreHashTable_A=kmerFreHashTable_A;
		SizeOfHashTable_A=sizeofHashTable_A;
		KmerFreHashTable_T=kmerFreHashTable_T;
		SizeOfHashTable_T=sizeofHashTable_T;
		KmerFreHashTable_G=kmerFreHashTable_G;
		SizeOfHashTable_G=sizeofHashTable_G;
		KmerFreHashTable_C=kmerFreHashTable_C;
		SizeOfHashTable_C=sizeofHashTable_C;
		ArraySize=scount;
		windowsize=Windowsize;
		Index=index;
		SplitSize=splitSize;
		AdapterFlag=adapterFlag;
		SizeAdapterArray=sizeAdapterArray;
		AdapterArray=adapterArray;
	}
	//Run.
    public String call() throws IOException
    {
		//Get special sequence.
		String [] SpecialLine = ReplaceStr.split("\t|\\s+");
		String Situation_N=SpecialLine[0];
		String Situation_A=SpecialLine[1];
		String Situation_T=SpecialLine[2];
		String Situation_G=SpecialLine[3];
		String Situation_C=SpecialLine[4];
		//Mark Reads.
		for(int i=Start;i<=end;i++)
		{
        	String [] SplitLines = ReadSetArray[i].split("\t|\\s+");
            String ReadID=SplitLines[0];
			String ReadString=SplitLines[1];
			String QSString=SplitLines[2];
			String QSValue=SplitLines[3];
        	int Readid=Integer.parseInt(ReadID);
			double QSRead=Double.parseDouble(QSValue);
			//Check PloyATGCN Tails.
			String Regular_A="[A][A][A][A][A][A]+";
			String Regular_T="[T][T][T][T][T][T]+";
			String Regular_G="[G][G][G][G][G][G]+";
			String Regular_C="[C][C][C][C][C][C]+";
			String Regular_N="[N][N][N][N][N][N]+";
			Pattern pattern_A = Pattern.compile(Regular_A);
			Pattern pattern_T = Pattern.compile(Regular_T);
			Pattern pattern_G = Pattern.compile(Regular_G);
			Pattern pattern_C = Pattern.compile(Regular_C);
			Pattern pattern_N = Pattern.compile(Regular_N);
            Matcher m_A = pattern_A .matcher(ReadString);
            Matcher m_T = pattern_T .matcher(ReadString);
            Matcher m_G = pattern_G .matcher(ReadString);
            Matcher m_C = pattern_C .matcher(ReadString); 
            Matcher m_N = pattern_N .matcher(ReadString);
            //Check Simple Repeats.
            String ChecKString=null;
            String MaxSubRepeats=CommonClass.GetSubRepeats(ReadString);
            if(MaxSubRepeats!=null)
            {
                String [] SplitLine=MaxSubRepeats.split(":");
                int SubRepeatFre=Integer.parseInt(SplitLine[1]);  
                for(int r=0;r<SubRepeatFre;r++)
                { 
    	         	if(r==0)
    	         	{
    	         	    ChecKString=SplitLine[0];
    	         	}
    	         	else
    	         	{
    	         		ChecKString+=SplitLine[0];
    	         	}
                }
            }
			//Process adapters.
			if(AdapterFlag==1)
			{
				for(int f=0;f<SizeAdapterArray;f++)
				{
					int AdapterLength=AdapterArray[f].length();
					double sim1=CommonClass.getSimilarityRatio(AdapterArray[f],ReadString.substring(0,AdapterLength));
					double sim2=CommonClass.getSimilarityRatio(AdapterArray[f],ReadString.substring(ReadString.length()-AdapterLength,ReadString.length()));				
					if(sim1>0.9)
					{
						ReadSetArray[i]=Readid+"\t"+ReadString.substring(AdapterLength,ReadString.length())+"\t"+QSString.substring(AdapterLength,ReadString.length())+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSString.substring(AdapterLength,ReadString.length()))+"\t"+"adapters";
					}
					if(sim2>0.9)
					{
						ReadSetArray[i]=Readid+"\t"+ReadString.substring(0,ReadString.length()-AdapterLength)+"\t"+QSString.substring(0,ReadString.length()-AdapterLength)+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSString.substring(0,ReadString.length()-AdapterLength))+"\t"+"adapters";
					}
				}
			}
			//Process Minimal quality,All_N,All_A All_T,All_G,All_C.
			if(Math.floor(QSRead)==MinSQ || ReadString.equals(Situation_N)|| ReadString.equals(Situation_A)|| ReadString.equals(Situation_T)|| ReadString.equals(Situation_G)|| ReadString.equals(Situation_C)||CommonClass.CheckLowBase(QSString)>=0.667)
    		{
    			ReadSetArray[Readid]=Readid+"\t"+"$"+"\t"+"$"+"\t"+"0"+"\t"+"ALL_ATGCN";
    			continue;
    		}
            //Check PloyATGC.
            if(m_A.find()||m_T.find()||m_G.find()||m_C.find())
			{
				   if(m_A.find()&&m_A.end()==ReadString.length())
				   {
					   ReadSetArray[Readid]=Readid+"\t"+ReadString.substring(0,m_A.start())+"\t"+QSString.substring(0,m_A.start())+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSString.substring(0,m_A.start()))+"\t"+"PloyA_Tails";
				   }
				   else if(m_T.find()&&m_T.end()==ReadString.length())
				   {
					   ReadSetArray[Readid]=Readid+"\t"+ReadString.substring(0,m_T.start())+"\t"+QSString.substring(0,m_T.start())+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSString.substring(0,m_T.start()))+"\t"+"PloyT_Tails";
				   }
				   else if(m_G.find()&&m_G.end()==ReadString.length())
				   {
					   ReadSetArray[Readid]=Readid+"\t"+ReadString.substring(0,m_G.start())+"\t"+QSString.substring(0,m_G.start())+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSString.substring(0,m_G.start()))+"\t"+"PloyG_Tails";
				   }
				   else if(m_C.find()&&m_C.end()==ReadString.length())
				   {
					   ReadSetArray[Readid]=Readid+"\t"+ReadString.substring(0,m_C.start())+"\t"+QSString.substring(0,m_C.start())+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSString.substring(0,m_C.start()))+"\t"+"PloyC_Tails";
				   }
				   else if(m_N.find()&&m_N.end()==ReadString.length())
				   {
					   ReadSetArray[Readid]=Readid+"\t"+ReadString.substring(0,m_N.start())+"\t"+QSString.substring(0,m_N.start())+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSString.substring(0,m_N.start()))+"\t"+"PloyN_Tails";
				   }
			}
            //Simple Repeats.
			if(ChecKString!=null && (ReadString.equals(ChecKString)||ReadString.endsWith(ChecKString)))
			{
				   if(ReadString.equals(ChecKString))
				   {
					    ReadSetArray[Readid]=Readid+"\t"+"$"+"\t"+"$"+"\t"+"0"+"\t"+"SimpleRepeats";
					    continue;
				   }
				   else if(ReadString.endsWith(ChecKString))
				   {
					   int CheckLength=ChecKString.length();
					   ReadSetArray[Readid]=Readid+"\t"+ReadString.substring(0,ReadString.length()-CheckLength)+"\t"+QSString.substring(0,ReadString.length()-CheckLength)+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSString.substring(0,ReadString.length()-CheckLength))+"\t"+"SimpleRepeats";
				   } 
			}
			//Process CharN-ATGC.
       	    if(ReadString.contains("N"))
       	    {
       	    	   int CountSplitLines=0;
       	    	   String SaveSplitLines[]=new String[ReadString.length()];
				   String [] SplitLineReads=ReadString.split("N");
				   for(int j=0;j<SplitLineReads.length;j++)
				   {
					   if(SplitLineReads[j].length()>=ReadString.length()/3)
					   {
						   int IndexSplit=ReadString.indexOf(SplitLineReads[j]);
						   SaveSplitLines[CountSplitLines++]=SplitLineReads[j]+"\t"+QSString.substring(IndexSplit,IndexSplit+SplitLineReads[j].length());  
					   }
				   }
				   if(CountSplitLines>=1)
				   {
					   //Order.
					   String Exch="";
					   for(int r=0;r<CountSplitLines;r++)
					   {
						   String [] SplitQSr=SaveSplitLines[r].split("\t");
						   for(int k=r+1;k<CountSplitLines;k++)
						   {
							   String [] SplitQSk=SaveSplitLines[k].split("\t");
							   if(CommonClass.GetAverageQualityScoreOfRead(SplitQSr[1])<CommonClass.GetAverageQualityScoreOfRead(SplitQSk[1]))
							   {
								   Exch=SaveSplitLines[r];
								   SaveSplitLines[r]=SaveSplitLines[k];
								   SaveSplitLines[k]=Exch;
							   }
						   }
					   }
					   //Splits.
					   String [] SplitQSp=SaveSplitLines[0].split("\t");
					   String MaxStr=SplitQSp[0];
					   String MaxQSstr=SplitQSp[1];
					   double MaxQSvalue=CommonClass.GetAverageQualityScoreOfRead(MaxQSstr);
					   ReadSetArray[Readid]=Readid+"\t"+MaxStr+"\t"+MaxQSstr+"\t"+MaxQSvalue+"\t"+"CharN-ATCG";
				   }
				   else
				   {
					   ReadSetArray[Readid]=Readid+"\t"+"$"+"\t"+"$"+"\t"+"0"+"\t"+"CharN-ATCG";
					   continue;
				   }
                   
       	    }
            //Output Process rate.
			if(i%10000==0)
			{
				  System.out.println("Remove-rate:"+Thread.currentThread().getName()+"->"+CommonClass.Changeformat(100*((double)RemoveRate/SplitSize))+"%");
			}
			RemoveRate++;
	    }	
		return "The thread:"+Index+"->running completed!";
    }
}


//MarkReads.
class TrimByMultipleThreads implements Callable<Object>{
	
	int Index=0;
	int Start;
	int end;
	int TrimRate=0;
	int RemoveRate=0;
	int ArraySize=0;
	int SizeOfHashTable_A=0;
	int SizeOfHashTable_T=0;
	int SizeOfHashTable_G=0;
	int SizeOfHashTable_C=0;
	int readlength=0;
	int windowsize=0;
	int SplitSize=0;
	int AdapterFlag=0;
	int SizeAdapterArray=0;
	double Threshold=0;
	double MinSQ=0;
	String AdapterArray[];
	String ReadSetArray[];
	String KmerFreHashTable_A[][];
	String KmerFreHashTable_T[][];
	String KmerFreHashTable_G[][];
	String KmerFreHashTable_C[][];
	String encoding="utf-8";
	String ReplaceStr="";
	String CheckStr="";
	//Construction.
	public TrimByMultipleThreads(int index,int Readlength,double threshold,String replaceStr,String checkStr,double minSQ,int Windowsize,int adapterFlag,int sizeAdapterArray,String adapterArray[],int scount,String readSetArray[],int splitSize,int StartPosition,int Endposition,String kmerFreHashTable_A[][],int sizeofHashTable_A,String kmerFreHashTable_T[][],int sizeofHashTable_T,String kmerFreHashTable_G[][],int sizeofHashTable_G,String kmerFreHashTable_C[][],int sizeofHashTable_C)
	{
		Start=StartPosition;
		end=Endposition;
	    readlength=Readlength;
		ReadSetArray=readSetArray;
		Threshold=threshold;
		MinSQ=minSQ;
		ReplaceStr=replaceStr;
		CheckStr=checkStr;
		KmerFreHashTable_A=kmerFreHashTable_A;
		SizeOfHashTable_A=sizeofHashTable_A;
		KmerFreHashTable_T=kmerFreHashTable_T;
		SizeOfHashTable_T=sizeofHashTable_T;
		KmerFreHashTable_G=kmerFreHashTable_G;
		SizeOfHashTable_G=sizeofHashTable_G;
		KmerFreHashTable_C=kmerFreHashTable_C;
		SizeOfHashTable_C=sizeofHashTable_C;
		ArraySize=scount;
		windowsize=Windowsize;
		Index=index;
		SplitSize=splitSize;
		AdapterFlag=adapterFlag;
		SizeAdapterArray=sizeAdapterArray;
		AdapterArray=adapterArray;
	}
	//Run.
    public String call() throws IOException
    {
		//Mark Reads.
		for(int i=Start;i<=end;i++)
		{
			if(ReadSetArray[i].length()>0)
			{
	      	    String [] SplitLines = ReadSetArray[i].split("\t|\\s+");
	            String ReadID=SplitLines[0];
				String ReadString=SplitLines[1];
				String QSString=SplitLines[2];
				String QSValue=SplitLines[3];
	      	    int Readid=Integer.parseInt(ReadID);
				double QSRead=Double.parseDouble(QSValue);
				//Check PloyATGC.
		    	if(ReadString.charAt(0)!='$' && ReadString.length()>=readlength/3 && QSRead<Threshold)
		    	{
		    	    int CountMinQSSave=0;
		    	    double MinQSSave[][] = new double[ReadString.length()][2];
		    	    //System.out.print("ReadID:"+ReadID+"\t");
		    	    //Check.
		    	    for(int c=0;c<=ReadString.length()-windowsize;c++)
		    	    {				   
		    			 double CurrentQS=CommonClass.CalculateAverageQualityScore(QSString.substring(c,c+windowsize));
		    		     double CurrentKF=CommonClass.CalculateAverageKmerScore(ReadString.substring(c, c+windowsize), KmerFreHashTable_A, SizeOfHashTable_A, KmerFreHashTable_T, SizeOfHashTable_T, KmerFreHashTable_G, SizeOfHashTable_G, KmerFreHashTable_C, SizeOfHashTable_C);
		    			 double CurrentGC=CommonClass.CalculateAverageGCcontent(ReadString.substring(c, c+windowsize));
		    		     MinQSSave[CountMinQSSave][0]=CurrentQS*CurrentKF*CurrentGC;
		    			 MinQSSave[CountMinQSSave][1]=c;
		    			 CountMinQSSave++;
		    		}
		    	    //Sort.
		    		double exchange1=0;
		    		double exchange2=0;
		    	    for(int r=0;r<CountMinQSSave;r++)
		    		{
		    			for(int e=r+1;e<CountMinQSSave;e++)
		    			{
		    				if(MinQSSave[r][0]<MinQSSave[e][0])
		    				{
		    					exchange1=MinQSSave[r][0];
		    					exchange2=MinQSSave[r][1];
		    					MinQSSave[r][0]=MinQSSave[e][0];
		    					MinQSSave[r][1]=MinQSSave[e][1];
		    					MinQSSave[e][0]=exchange1;
		    					MinQSSave[e][1]=exchange2;
		    				}
		    		    }
		    		}
		    		int MinIndex1=(int)MinQSSave[0][1];
		    		int MinIndex2=(int)MinQSSave[1][1];
		    		//System.out.print("MinIndex1, MinIndex2:"+MinIndex1+","+MinIndex2+"\n");
					//Free
		    		MinQSSave=null;
					//Process.
					if(MinIndex1<MinIndex2)
					{
						if(MinIndex2-MinIndex1>windowsize)
						{
							//Read String.
							String ReadSubStr1=ReadString.substring(0, MinIndex1);
							String ReadSubStr2=ReadString.substring(MinIndex1+windowsize,MinIndex2);
							String ReadSubStr3=ReadString.substring(MinIndex2+windowsize,ReadString.length());
							//QS string.
							String QSSubStr1=QSString.substring(0, MinIndex1);
							String QSSubStr2=QSString.substring(MinIndex1+windowsize,MinIndex2);
							String QSSubStr3=QSString.substring(MinIndex2+windowsize,ReadString.length());
							//QS value.	
							double QS1=0.7*CommonClass.GetAverageQualityScoreOfRead(QSSubStr1)+0.3*QSSubStr1.length();
							double QS2=0.7*CommonClass.GetAverageQualityScoreOfRead(QSSubStr2)+0.3*QSSubStr1.length();
							double QS3=0.7*CommonClass.GetAverageQualityScoreOfRead(QSSubStr3)+0.3*QSSubStr1.length();
							double MaxQS=QS1>QS2?(QS1>QS3?QS1:QS3):(QS2>QS3?QS2:QS3);
				    		//Check.
				    		if(QS1==MaxQS)
				    		{
				    			ReadSetArray[Readid]=Readid+"\t"+ReadSubStr1+"\t"+QSSubStr1+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSSubStr1)+"\t"+"Case1+1";
				    		}
				    		else if(QS2==MaxQS)
				    		{
				    			ReadSetArray[Readid]=Readid+"\t"+ReadSubStr2+"\t"+QSSubStr2+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSSubStr2)+"\t"+"Case2+1";	
				    		}
				    		else if(QS3==MaxQS)
				    		{
				    			ReadSetArray[Readid]=Readid+"\t"+ReadSubStr3+"\t"+QSSubStr3+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSSubStr3)+"\t"+"Case3+1";	
				    		}
						}
						else
						{
							//Read String.
							String ReadSubStr1=ReadString.substring(0, MinIndex1);
							String ReadSubStr3=ReadString.substring(MinIndex2+windowsize,ReadString.length());
							//QS string.
							String QSSubStr1=QSString.substring(0, MinIndex1);
							String QSSubStr3=QSString.substring(MinIndex2+windowsize,ReadString.length());
							//QS value.	
							double QS1=0.7*CommonClass.GetAverageQualityScoreOfRead(QSSubStr1)+0.3*QSSubStr1.length();
							double QS3=0.7*CommonClass.GetAverageQualityScoreOfRead(QSSubStr3)+0.3*QSSubStr1.length();
							double MaxQS=0;
							if(QS1>QS3)
							{
								MaxQS=QS1;
							}
							else if(QS1<QS3)
							{
								MaxQS=QS3;
							}
						    //Check.
						    if(QS1==MaxQS)
						    {
						    	ReadSetArray[Readid]=Readid+"\t"+ReadSubStr1+"\t"+QSSubStr1+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSSubStr1)+"\t"+"Case1-1";
						    }
						    else if(QS3==MaxQS)
						    {
						    	ReadSetArray[Readid]=Readid+"\t"+ReadSubStr3+"\t"+QSSubStr3+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSSubStr3)+"\t"+"Case3-1";	
						    }
						}
					}
					else if(MinIndex1>MinIndex2) 
					{
						if(MinIndex1-MinIndex2>windowsize)
						{
							//Read String.
							String ReadSubStr1=ReadString.substring(0, MinIndex2);
							String ReadSubStr2=ReadString.substring(MinIndex2+windowsize,MinIndex1);
							String ReadSubStr3=ReadString.substring(MinIndex1+windowsize,ReadString.length());
							//QS string.
							String QSSubStr1=QSString.substring(0, MinIndex2);
							String QSSubStr2=QSString.substring(MinIndex2+windowsize,MinIndex1);
							String QSSubStr3=QSString.substring(MinIndex1+windowsize,ReadString.length());
							//QS value.	
							double QS1=0.7*CommonClass.GetAverageQualityScoreOfRead(QSSubStr1)+0.3*QSSubStr1.length();
							double QS2=0.7*CommonClass.GetAverageQualityScoreOfRead(QSSubStr2)+0.3*QSSubStr1.length();
							double QS3=0.7*CommonClass.GetAverageQualityScoreOfRead(QSSubStr3)+0.3*QSSubStr1.length();
							double MaxQS=QS1>QS2?(QS1>QS3?QS1:QS3):(QS2>QS3?QS2:QS3);
					    	//Check.
					    	if(QS1==MaxQS)
					    	{
					    		ReadSetArray[Readid]=Readid+"\t"+ReadSubStr1+"\t"+QSSubStr1+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSSubStr1)+"\t"+"Case1-1";
					    	}
					    	else if(QS2==MaxQS)
					    	{
					    		ReadSetArray[Readid]=Readid+"\t"+ReadSubStr2+"\t"+QSSubStr2+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSSubStr2)+"\t"+"Case2-1";	
					    	}
					    	else if(QS3==MaxQS)
					    	{
					    		ReadSetArray[Readid]=Readid+"\t"+ReadSubStr3+"\t"+QSSubStr3+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSSubStr3)+"\t"+"Case3-1";	
					    	}
						 }
						 else
						 {
							//Read String.
							String ReadSubStr1=ReadString.substring(0, MinIndex2);
							String ReadSubStr3=ReadString.substring(MinIndex1+windowsize,ReadString.length());
							//QS string.
							String QSSubStr1=QSString.substring(0, MinIndex2);
							String QSSubStr3=QSString.substring(MinIndex1+windowsize,ReadString.length());
							//QS value.	
							double QS1=0.7*CommonClass.GetAverageQualityScoreOfRead(QSSubStr1)+0.3*QSSubStr1.length();
							double QS3=0.7*CommonClass.GetAverageQualityScoreOfRead(QSSubStr3)+0.3*QSSubStr1.length();
							double MaxQS=0;
							if(QS1>QS3)
							{
								MaxQS=QS1;
							}
							else if(QS1<QS3)
							{
								MaxQS=QS3;
							}
						    //Check.
						    if(QS1==MaxQS)
						    {
						    	ReadSetArray[Readid]=Readid+"\t"+ReadSubStr1+"\t"+QSSubStr1+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSSubStr1)+"\t"+"Case1-1";
						    }
						    else if(QS3==MaxQS)
						    {
						    	ReadSetArray[Readid]=Readid+"\t"+ReadSubStr3+"\t"+QSSubStr3+"\t"+CommonClass.GetAverageQualityScoreOfRead(QSSubStr3)+"\t"+"Case3-1";	
						    }
						 }
					 }
				}
		    	else if(ReadString.charAt(0)!='$' && QSRead>=Threshold && ReadString.length()>=readlength/3)
		    	{
		    		 //Case12
		    		 ReadSetArray[Readid]=Readid+"\t"+ReadString+"\t"+QSString+"\t"+"Case12";
		    	}
				else if(ReadString.charAt(0)=='$' || ReadString.length()<readlength/3)
				{
					 //Case13
		    		 ReadSetArray[Readid]=Readid+"\t"+"$"+"\t"+"$"+"\t"+"0"+"\t"+"Case13";
				}
			}
            //Output Process rate.
			if(i%10000==0)
			{
				 System.out.println("Trim-rate:"+Thread.currentThread().getName()+"->"+CommonClass.Changeformat(100*((double)TrimRate/SplitSize))+"%");
			}
			TrimRate++;
	    }	
		return "The thread:"+Index+"->running completed!";
    }
}

//MergeFiles.
class MergeFiles {
	//Main.
	public static void main(String[] args) throws IOException, Exception, ExecutionException {
	    String Left_FilePath=args[0];
	    String Right_FilePath=args[1];
	    String WritePath=args[2];
	    //Count.
	    int CountLines=CommonClass.getFileLine(Left_FilePath);
	    String SaveLeftFile[]=new String[CountLines];
	    String SaveRightFile[]=new String[CountLines];
	    int RealSize_Left=CommonClass.FastaToArray(Left_FilePath,SaveLeftFile);
	    int RealSize_Right=CommonClass.FastaToArray(Right_FilePath,SaveRightFile);
	    System.out.println("RealSize_Left:"+RealSize_Left+"\t"+"RealSize_Right:"+RealSize_Right);
	    //Merge.
	    int Unpaired_Left=0;
	    int Unpaired_Right=0;
	    int Paired_Left=0;
	    int Paired_Right=0;
	    for(int w=0;w<RealSize_Left;w++)
	    {
	    	String [] SplitLeft = SaveLeftFile[w].split("\t|\\s+");
	    	String [] SplitRight = SaveRightFile[w].split("\t|\\s+");
	    	if(SplitLeft[1].equals("$")&&!SplitRight[1].equals("$"))
	    	{
				FileWriter writer= new FileWriter(WritePath+"Unpaired_right.fq",true);
	            writer.write("@"+(Unpaired_Right++)+":"+SplitRight[1].length()+"\t"+SplitRight[3]+"\n"+SplitRight[1]+"\n"+"+"+"\n"+SplitRight[2]+"\n");
	            writer.close();
	    	}
	    	else if(!SplitLeft[1].equals("$")&&SplitRight[1].equals("$"))
	    	{
				FileWriter writer= new FileWriter(WritePath+"Unpaired_left.fq",true);
	            writer.write("@"+(Unpaired_Left++)+":"+SplitLeft[1].length()+"\t"+SplitLeft[3]+"\n"+SplitLeft[1]+"\n"+"+"+"\n"+SplitLeft[2]+"\n");
	            writer.close();
	    	}
	    	else if(!SplitLeft[1].equals("$")&&!SplitRight[1].equals("$"))
	    	{
				FileWriter writer1= new FileWriter(WritePath+"Trimed.paired_left.fq",true);
	            writer1.write("@"+(Paired_Left++)+":"+SplitLeft[1].length()+"\t"+SplitLeft[3]+"\n"+SplitLeft[1]+"\n"+"+"+"\n"+SplitLeft[2]+"\n");
	            writer1.close();
	            
				FileWriter writer2= new FileWriter(WritePath+"Trimed.paired_right.fq",true);
	            writer2.write("@"+(Paired_Right++)+":"+SplitRight[1].length()+"\t"+SplitRight[3]+"\n"+SplitRight[1]+"\n"+"+"+"\n"+SplitRight[2]+"\n");
	            writer2.close();
	    	}
	    }
	}
}
//GenerateFastaFromFastqFiles.
class GenerateFastaFromFastqFiles {
    //Main.	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String short_1_A_Path = args[0];
		String short_1_B_Path = args[1];
		String OutPutPath=args[2];
		//short_1_A Path 
		int short_1_A_Size = CommonClass.getLineOfFastq(short_1_A_Path);
		String[] short_1_A_Array = new String[short_1_A_Size];
		int scount_short_1_A = CommonClass.readTxtFile(short_1_A_Path, short_1_A_Array);	
	    System.out.println("The size of short_1_A_Array is:"+scount_short_1_A);
		//short_1_B Path 
		int short_1_B_Size = CommonClass.getLineOfFastq(short_1_B_Path);
		String[] short_1_B_Array = new String[short_1_B_Size];
		int scount_short_1_B = CommonClass.readTxtFile(short_1_B_Path, short_1_B_Array);	
	    System.out.println("The size of short_1_B_Array is:"+scount_short_1_B);
		//Generate Fasta File.
	    int Num=0;
		for(int m=0;m<scount_short_1_A;m++)
		{
			FileWriter writer= new FileWriter(OutPutPath,true);
            writer.write(">"+(Num++)+"\n"+short_1_A_Array[m]+"\n"+">"+(Num++)+"\n"+short_1_B_Array[m]+"\n");
            writer.close();
		}
	}
}