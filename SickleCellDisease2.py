import math
 
I = ('ATT', 'ATC', 'ATA')                                       #Storing codons for different amino acids 
L = ('CTT', 'CTC', 'CTA', 'CTG', 'TTA', 'TTG')
V = ('GTT', 'GTC', 'GTA', 'GTG')
F = ('TTT', 'TTC')
M = ('ATG')
 
def translate(sequence):
 
    AminoAcid = ""
 
    i = math.ceil(len(sequence)/3)                              #Formula for number of interations in loop
    for j in range(i):
        codon = sequence[j*3:j*3+3]                             #Formula to capture each codon
        if len(codon) != 3:
            continue
        if codon in I:
            AminoAcid += "I"
        elif codon in L:
            AminoAcid += "L"
        elif codon in V:
            AminoAcid += "V"
        elif codon in F:
            AminoAcid += "F"
        elif codon in M:
            AminoAcid += "M"
        else:
            AminoAcid += "X"
         
    print(AminoAcid)
     
def mutate():
 
    DNAFile = open("DNA.txt", "r")
    mutatedDNA = open("mutatedDNA.txt", "w")                    #Creating text file to write new DNA sequence
    normalDNA = open("normalDNA.txt", "w")
     
    for line in DNAFile:
         
        for nucleotide in line:
            if nucleotide == 'a':
                mutatedDNA.write('T')                           #Writing the correct nucleotide to file
                normalDNA.write('A')
            else:
                mutatedDNA.write(nucleotide)
                normalDNA.write(nucleotide)


    DNAFile.close()
    mutatedDNA.close()
    normalDNA.close()
 
def txtTranslate(DNAtype, File):
     
    print('\n' + DNAtype)
    print("The DNA sequence translated is:")
    for line in File:
        translate(line.replace('\n', ''))                       #Removing new line string method passing eacg line to tranlaste function
    File.close()
        
def main():
     
    print("DNA Sequence Translator:\n")
    DNAseq = input("Enter DNA sequence to translate:\n")
    print("The DNA sequence translated is:")
    translate(DNAseq)
     
    mutate()
    txtTranslate("Mutated DNA from text file", open("MutatedDNA.txt", "r"))    #Passing two arguements to txtTranlate function
    txtTranslate("Normal DNA from text file", open("normalDNA.txt", "r"))

main()
