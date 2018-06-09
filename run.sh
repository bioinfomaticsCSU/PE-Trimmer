#!/bin/bash
echo  "*******************************************************************************************"
echo "Copyright (C) 2017 Jianxin Wang(jxwang@mail.csu.edu.cn), Xingyu Liao(liaoxingyu@csu.edu.cn)"
echo "School of Information Science and Engineering"
echo "Central South University"
echo "ChangSha"
echo "CHINA, 410083"
echo  "*******************************************************************************************"
echo  "Step-1: Get configuration information"
TEST=$(cat ./config.txt)
i=0;
for p in $TEST; do
	arra[$i]=$p
	i=$((i+1))
done
for ((i=0;i<${#arra[@]};i++))
    do
    if [ $i = 0 ] 
    then
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       home=$var
    fi
    if [ $i = 1 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       lib_left_name=$var
    fi
    if [ $i = 2 ] 
    then
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       lib_right_name=$var
    fi
    if [ $i = 3 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Readlength=$var
    fi
    if [ $i = 4 ] 
    then
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       WindowSize=$var
    fi
    if [ $i = 5 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       UserThreshold=$var
    fi
	if [ $i = 6 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Reference=$var
    fi
    if [ $i = 7 ] 
    then
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       dsk_bin=$var
    fi
    if [ $i = 8 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_bin=$var
    fi
done 
#define
echo  "Step-2: Compiling public classes"
javac  $home/Program/Trimer.java
#Clean
rm -rf $home/TrimInformations
rm -rf  $home/dsk
rm -rf  $home/Log
rm -rf  $home/Log/dsk
#makdir
mkdir $home/TrimInformations
mkdir $home/dsk
mkdir $home/Log
mkdir $home/Log/dsk
mkdir $home/Log/RemoveTS
mkdir $home/Log/Trimming
##########################################################################################################################################
echo  "Step-3: Error correction [Current status is off]"
cd $home/lib
#rm -rf $home/lib/karect_*
#rm -rf $home/lib/input_file.txt
#rm -rf $home/lib/res_*
#rm -rf $home/lib/temp_res_*
#rm -rf $home/lib/karect_$lib_left_name.fastq
#rm -rf $home/lib/karect_$lib_right_name.fastq
#karect -correct -threads=24 -matchtype=hamming -celltype=haploid -inputfile=$home/lib/$lib_left_name.fastq -inputfile=$home/lib/$lib_right_name.fastq > $home/Log/ErrorCorrection/ErrorCorrection_lib1.fa
#cp $home/lib/karect_$lib_left_name.fastq  $home/lib/$lib_left_name.corrected.fastq
#cp $home/lib/karect_$lib_right_name.fastq $home/lib/$lib_right_name.corrected.fastq
#cd $home
rm -rf $home/lib/$lib_left_name.cor.fastq
rm -rf $home/lib/$lib_right_name.cor.fastq
cp $home/lib/$lib_left_name.fastq  $home/lib/$lib_left_name.cor.fastq
cp $home/lib/$lib_right_name.fastq $home/lib/$lib_right_name.cor.fastq
##########################################################################################################################################
echo  "Step-4: File format conversion [FASTQ->FILE]"
rm -rf $home/lib/short1.fasta
rm -rf $home/lib/LeftFile.fa
rm -rf $home/lib/RightFile.fa
java -classpath  $home/Program/ FormatConversion $home/lib/$lib_left_name.cor.fastq $home/lib/$lib_right_name.cor.fastq $home/lib/
##########################################################################################################################################
echo  "Step-5: Generate Unique Kmer [dsk]"
cd $dsk_bin
./dsk  -file $home/lib/short1.fasta -kmer-size $WindowSize -abundance-min 1 > $home/Log/dsk/out.log 2>&1
./dsk2ascii -file short1.h5 -out short1.txt > $home/Log/dsk/out.log 2>&1
rm -rf $home/dsk/*
cp short1.txt $home/dsk/KmerSet.fa
rm -rf short1.txt
rm -rf short1.h5
##########################################################################################################################################
cd $home
echo  "Step-6: Divide the kmer hashtable into multiple sub-tables"
rm -rf $home/dsk/KmerSet_A.fa
rm -rf $home/dsk/KmerSet_T.fa
rm -rf $home/dsk/KmerSet_G.fa
rm -rf $home/dsk/KmerSet_C.fa
java -classpath  $home/Program/ GenerateBlockedKmerHashTable $home/dsk/KmerSet.fa  $home/dsk/
##########################################################################################################################################
echo  "Step-7: Generate the marginal range of trimming"
rm -rf $home/lib/QSStatistics_left.fa
rm -rf $home/lib/QSStatistics_right.fa
rm -rf $home/lib/MarginalValue_left.fa
rm -rf $home/lib/MarginalValue_right.fa
java -classpath  $home/Program/ GetMarginalValueOfQS $home/lib/QSStatistics_left.fa $home/lib/MarginalValue_left.fa $home/lib/LeftFile.fa
java -classpath  $home/Program/ GetMarginalValueOfQS $home/lib/QSStatistics_right.fa $home/lib/MarginalValue_right.fa $home/lib/RightFile.fa
##########################################################################################################################################
echo  "Step-8: Remove technical sequences"
start=$(date +%s)
rm -rf $home/TrimInformations/*
java -classpath  $home/Program/ peTrimer $home/dsk/ $UserThreshold  $WindowSize  $Readlength $home/lib/LeftFile.fa  $home/lib/MarginalValue_left.fa $home/AdapterStrings.txt  $home/TrimInformations/Short1.left.Removed.fa > $home/Log/RemoveTS/left.log
java -classpath  $home/Program/ peTrimer $home/dsk/ $UserThreshold  $WindowSize  $Readlength $home/lib/RightFile.fa $home/lib/MarginalValue_right.fa $home/AdapterStrings.txt $home/TrimInformations/Short1.right.Removed.fa > $home/Log/RemoveTS/right.log
echo  "Step-9: Trimming paired-end reads"
java -classpath  $home/Program/ TrimerPairedReads $home/dsk/ $UserThreshold  $WindowSize  $Readlength $home/TrimInformations/Short1.left.Removed.fa  $home/lib/MarginalValue_left.fa $home/AdapterStrings.txt  $home/TrimInformations/Short1.left.trimed.fa > $home/Log/Trimming/left.log
java -classpath  $home/Program/ TrimerPairedReads $home/dsk/ $UserThreshold  $WindowSize  $Readlength $home/TrimInformations/Short1.right.Removed.fa $home/lib/MarginalValue_right.fa $home/AdapterStrings.txt $home/TrimInformations/Short1.right.trimed.fa > $home/Log/Trimming/right.log
echo  "Step-10: Merge files"
java -classpath  $home/Program/ MergeFiles $home/TrimInformations/Short1.left.trimed.fa $home/TrimInformations/Short1.right.trimed.fa $home/TrimInformations/