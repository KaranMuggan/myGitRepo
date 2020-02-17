#Compulsory Task 1
#Follow these steps:
#● Create a program called, ‘SickleCellDisease.py’. You will simulate the
##effects of the Single Nucleotide Polymorphism that leads to this genetic
#disease.
#● Write a function called ‘translate’ that, when given a DNA sequence of
#arbitrary length, the program identifies and returns the amino acid
#sequence of the DNA using the amino acid SLC code found in that table.
###E.g DNA Input: ATTATTATT
#Output: III (representing: Isoleucine, Isoleucine, Isoleucine )
#● There are many different amino acids, so this may get a bit repetitive. Just
#do the first five Amino Acids (i.e I, L, V, F M) and make any other codon be
#printed as the amino acid 'X' . So basically, you would use an if - elif - elif ....
#else structure to translate each codon of DNA into the correct Amino Acid.
#● Note that the program must be able to handle DNA sequences that are
#not of a length divisible by 3.
#● Hint:
#len(DNA) - (Will return the length of a String)
#DNA[0:3] - (Will get the first 3 characters of the string stored in DNA num = #3)
#DNA[0:num] - (This will work too!)

SLC={'I':['ATT', 'ATC', 'ATA'],
'L':['CTT', 'CTC', 'CTA', 'CTG', 'TTA', 'TTG'],             #the dictionary SLC containing a list with the codons and their relevant amino acid
'V':['GTT', 'GTC', 'GTA', 'GTG'],
'F':['TTT', 'TTC'],
'M':['ATG']}

dna_seq = input("Please enter a DNA sequence:\n")           #requests user to input a DNA sequence

def translate(dna_seq):
    num = 3                                                 #num represents the 3rd position in the string
    range = 0                                               #range represents the 0th position in the string
    seq = ''                                                #string broken into 3's creates amino acid which is added into seq
    while num<len(dna_seq)+1:                               #while the num(3) is less than the length of the string entered
        x = dna_seq[range:num]                              #x represents each 3 letter string
        for key in SLC.keys():                              #for each key in the SLC dictionary
            if x in SLC[key]:                               #if the input contains a matching codon in the dictionary
                seq+=key                                    #the key from dictionary that corresponds to a codon is added to the sequence
        range+=3                                            #the range moves 3 spaces forward to the next end of 3
        num+=3                                              #the num moves 3 spaces to the next start of a 3 letter string
    return seq

print(translate(dna_seq))                                   #prints out the translated dna sequence

