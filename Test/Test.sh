#!/bin/bash
echo  "*******************************************************************************************"
echo "Copyright (C) 2017 Jianxin Wang(jxwang@mail.csu.edu.cn), Xingyu Liao(liaoxingyu@csu.edu.cn)"
echo "School of Information Science and Engineering"
echo "Central South University"
echo "ChangSha"
echo "CHINA, 410083"
echo  "*******************************************************************************************"
echo  "Step-1: Get configuration information"
TEST=$(cat ../config.txt)
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
#Clean
rm -rf  $home/Test/Alignment
rm -rf  $home/Test/Assembly
rm -rf  $home/Test/Quast
#makdir
mkdir $home/Test/Alignment
mkdir $home/Test/Assembly
mkdir $home/Test/Quast
mkdir $home/Log/RemoveTS
mkdir $home/Log/Trimming
mkdir $home/Log/Quast
##########################################################################################################################################
echo  "Step-1: Alignment [bowtie2]"
java -classpath  $home/Program/ GenerateFastaFromFastqFiles $home/TrimInformations/Trimed.paired_left.fq $home/TrimInformations/Trimed.paired_right.fq $home/TrimInformations/$Reference.short.fasta
##########################################################################################################################################
bowtie2-build  $home/reference/$Reference.fasta  $home/reference/fre > $home/Test/Alignment/$Reference.build.log 2>&1 
bowtie2 -p 64 -x $home/reference/fre -f $home/TrimInformations/$Reference.short.fasta -S $home/Test/Alignment/$Reference.sam > $home/Test/Alignment/$Reference.Align.log 2>&1 
echo  "Step-2: Assembly [Abyss]"
KmerSize=41
Trim_data1_left=$home/TrimInformations/Trimed.paired_left.fq
Trim_data1_right=$home/TrimInformations/Trimed.paired_right.fq
##########################################################################################################################################
#delete
rm -rf $Abyss_bin/*.fa
rm -rf $Abyss_bin/*.dot
rm -rf $Abyss_bin/*.path
rm -rf $Abyss_bin/*.path1
rm -rf $Abyss_bin/*.path2
rm -rf $Abyss_bin/*.path3
rm -rf $Abyss_bin/*-stats
rm -rf $Abyss_bin/*.hist
rm -rf $Abyss_bin/*.adj
rm -rf $Abyss_bin/*.dist
rm -rf $Abyss_bin/contigs.fa
rm -rf $Abyss_bin/scaffolds.fa
#Add
rm -rf $Abyss_bin/*.tab
rm -rf $Abyss_bin/*.md
rm -rf $Abyss_bin/*.csv
rm -rf $Abyss_bin/*.fai
rm -rf $Abyss_bin/*.dot1
cd $Abyss_bin
abyss-pe k=$KmerSize l=40 n=5 s=1000 name=liaoxingyu in="$Trim_data1_left $Trim_data1_right" > $home/Test/Assembly/Abyss_assembly_before.log
cp liaoxingyu-6.fa  contigs.fa
cp liaoxingyu-8.fa  scaffolds.fa
cp $Abyss_bin/contigs.fa $home/Test/Assembly/contigs.fa
cp $Abyss_bin/scaffolds.fa $home/Test/Assembly/scaffolds.fa
#delete
rm -rf $Abyss_bin/*.fa
rm -rf $Abyss_bin/*.dot
rm -rf $Abyss_bin/*.path
rm -rf $Abyss_bin/*.path1
rm -rf $Abyss_bin/*.path2
rm -rf $Abyss_bin/*.path3
rm -rf $Abyss_bin/*-stats
rm -rf $Abyss_bin/*.hist
rm -rf $Abyss_bin/*.adj
rm -rf $Abyss_bin/*.dist
rm -rf $Abyss_bin/contigs.fa
rm -rf $Abyss_bin/scaffolds.fa
#Add
rm -rf $Abyss_bin/*.tab
rm -rf $Abyss_bin/*.md
rm -rf $Abyss_bin/*.csv
rm -rf $Abyss_bin/*.fai
rm -rf $Abyss_bin/*.dot1
##########################################################################################################################################
echo  "Step-3: Evaluation [Quast]"
quast.py  $home/Test/Assembly/contigs.fa $home/Test/Assembly/scaffolds.fa -R $home/reference/$Reference.fasta -o $home/Test/Quast/ > $home/Log/Quast/out.log