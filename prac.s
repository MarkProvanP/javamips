# CS 2002 Assembler Practical Week 11
# School of Computer Science
# University of St Andrews
# Ian Gent April 2015
#
# You must provide global memparity, memcompute, and testcall
#
# For full details of requirements consult the practical specification on studres 

        .data

msgOne: .asciiz "Part One Finished!\n"
msgTwo: .asciiz "Part Two Finished!\n"
msgThree:.asciiz "Part Three Finished!\n"
newline: .asciiz "\n"

errorRVNEQ: .asciiz "Error! Return value was not what it should have been!\n"
errorSPNEQ: .asciiz "Error! Stack pointer was not what it should have been!\n"
errorGPNEQ: .asciiz "Error! General pointer was not what it should have been!\n"
errorS0NEQ: .asciiz "Error! $s0 was not what it should have been!\n"
errorS1NEQ: .asciiz "Error! $s1 was not what it should have been!\n"
errorS2NEQ: .asciiz "Error! $s2 was not what it should have been!\n"
errorS3NEQ: .asciiz "Error! $s3 was not what it should have been!\n"
errorS4NEQ: .asciiz "Error! $s4 was not what it should have been!\n"
errorS5NEQ: .asciiz "Error! $s5 was not what it should have been!\n"
errorS6NEQ: .asciiz "Error! $s6 was not what it should have been!\n"
errorS7NEQ: .asciiz "Error! $s7 was not what it should have been!\n"
errorFPNEQ: .asciiz "Error! Function pointer was not what it should have been!\n"
errorPARNEQ: .asciiz "Error! Parity of stack contents was not what it should have been!\n"
errorTestOne: .asciiz "Expect $s0 error!\n"
errorTestTwo: .asciiz "Expect stack parity error!\n"
errorTestThree: .asciiz "Expect general pointer error!\n"
        .align 4
numwords:
        .word 3
wordstart:
        .word 0x0FFFFFFF
        .word 0x0F0F0F0F
        .word 0x0000F0F0
        .word 0x00F00000

        .align 4

        .text

main:
        move $s0, $ra   # Store the current return address
        
        #### PART ONE ####
        
        la $a0, wordstart
        jal memparity
        move $s0, $v0 # Save the return value in $s0
        
        li $v0, 4
        la $a0, msgOne # Print out a message to say that part 1 has completed!
        syscall
        
        li $v0, 1
        move $a0, $s0 # Print out the result from the first part
        syscall
        
        li $v0, 4
        la $a0, newline # Print out a newline character
        syscall
        
        #### PART TWO ####
	
	la $a0, wordstart
	la $a1, testxorfunc
        jal memcompute
        move $s0, $v0

        li $v0, 4
        la $a0, msgTwo # Print out a message to say that part 2 has completed!
        syscall
        
        li $v0, 1
        move $a0, $s0 # Print out the result from the first part
        syscall
        
        li $v0, 4
        la $a0, newline # Print out a newline character
        syscall
	
	#### PART THREE - CORRECT FUNCTION ####
	
	la $a0, testxorfunc
	lw $a1, wordstart
	lw $a2, wordstart + 4
	li $a3, 0x00F0F0F0
        jal testcall
        
        li $v0, 4
        la $a0, msgThree # Print out a message to say that part 3 has completed!
        syscall
        
        #### PART THREE - INCORRECT FUNCTION 1 ####
        
        li $v0, 4
        la $a0, errorTestOne
        syscall
        
        la $a0, testbrokens0
        lw $a1, wordstart
        lw $a2, wordstart + 4
        li $a3, 0x00F0F0F0
        jal testcall
        
        #### PART THREE - INCORRECT FUNCTION 2 ####
        
        li $v0, 4
        la $a0, errorTestTwo
        syscall
        
        la $a0, testbrokensp
        lw $a1, wordstart
        lw $a2, wordstart + 4
        li $a3, 0x00F0F0F0
        jal testcall
        
        #### PART THREE - INCORRECT FUNCTION 3 ####
        
        li $v0, 4
        la $a0, errorTestThree
        syscall
        
        la $a0, testbrokengp
        lw $a1, wordstart
        lw $a2, wordstart + 4
        li $a3, 0x00F0F0F0
        jal testcall
        
        #### FINISHED! ####
        
        li $v0, 10
        syscall		# Exit

##############
## PART ONE ##
##############

memparity:
        lw $t1, numwords    # $t1 is now the number of words to read in and XOR
        move $v0, $zero     # Load zero into return value, so that if there are no words to XOR, the correct value is ready immediately.
memparloop:
        beq $t1, $zero, memparcomplete # If the value of $t1 is zero, then jump to the end of the function immediately
        lw $t0, 0($a0)      # Load the contents of the memory address stored in $a0 to $t0
        xor $v0, $v0, $t0   # XOR the current $v0 value against $t0, which is the word pointed to by $a0
        addiu $a0, $a0, 4   # Increment $a0 by 4, so that it now points to the next word in memory
        addiu $t1, $t1, -1  # Decrement $t1 since a move has been made.
        j memparloop        # Jump back to loop again
memparcomplete:
        jr $ra              # Return to the calling function
       
####################################
## PART ONE - MODIFIED FOR PART 3 ##
####################################

memparityarg:
        # $a1 is the number of words to read in and XOR
        move $v0, $zero     # Load zero into return value, so that if there are no words to XOR, the correct value is ready immediately.
memparargloop:
        beq $a1, $zero, memparargcomplete # If the value of $t1 is zero, then jump to the end of the function immediately
        lw $t0, 0($a0)      # Load the contents of the memory address stored in $a0 to $t0
        xor $v0, $v0, $t0   # XOR the current $v0 value against $t0, which is the word pointed to by $a0
        addiu $a0, $a0, 4   # Increment $a0 by 4, so that it now points to the next word in memory
        addiu $a1, $a1, -1  # Decrement $t1 since a move has been made.
        j memparargloop        # Jump back to loop again
memparargcomplete:
        jr $ra              # Return to the calling function

##############
## PART TWO ##
##############

memcompute:               # Part 2 of Practical
        lw $t1, numwords  # $t1 is now the number of words to read in and process
        move $v0, $zero      # Load zero into the return value
        # Case 1, zero input words
        beq $t1, 0, memcomputecomplete # If the value of $t1 is zero, then jump to the end of the function immediately
        
        # Case 2, one input word
        lw $v0, ($a0)	  # Copy the word stored at the memory address in $a0 to $v0, since it may be the first and only return result
       	beq $t1, 1, memcomputecomplete # If there is only one value left, then just return immediately.
       	
	# Case 3, multiple input words
	addiu $sp, $sp, -32  # Make stack frame, with space for all the things we need to save
	sw $fp, 28($sp)      # Save frame pointer
	sw $ra, 24($sp)      # Save return address
	sw $s1, 20($sp)
	sw $s2, 4($sp)    # Save current contents of $s2 to stack
	sw $s3, 8($sp)    # Save current contents of $s3 to stack
        move $s2, $a0     # Save the value of $a0 - the current memory address to work on - to $s2 ...
        move $s3, $a1     # ... and $a1 - the function to use - to $s1 so that $a0 and $a1 may be used by the function to be called
       	sw $t1, 12($sp) # Save current value of $t1 to stack
	lw $s1, ($s2)    # The first argument of the next function needs to be set up.
memcomputeloop:
	beq $t1, 1, memcomputeloopcomplete
       	move $a0, $s1	# The first argument of the function. The first time through the loop, this is the value of the word at $s2. The next time around, it will be the result of the last time it was run.
	lw $a1, 4($s2)  # The second argument of the function to be called is the word stored after the memory address in $s2
	jalr $s3        # The function called is at the memory address stored in $s3
       	move $s1, $v0   # Save the result of the operation in $s1
	addiu $s2, $s2, 4 # Increment $s2 by 4, so that it now points to the next word in memory
       	move $a0, $s2     # Restore the value of $a0 - the current memory address to work on - from $s0...
       	move $a1, $s3     # ... and $a1 - the function to use - from $s1
	lw $t1, 12($sp)   # Restore value of $t1
	addiu $t1, $t1, -1 # Decrement $t1 since a move has been made
	sw $t1, 12($sp)   # Save the new value of $t1 in the stack again
	j memcomputeloop  # Loop back to perform memcompute on the next thing
memcomputeloopcomplete:
	lw $s1, 20($sp)
	lw $s2, 4($sp)    # Restore original contents of $s2
	lw $s3, 8($sp)    # Restore original contents of $s3
	lw $fp, 28($sp)    # Restore $fp
	lw $ra, 24($sp)    # Restore $ra
	addiu $sp, $sp, 32 # Restore $sp
memcomputecomplete:
        jr $ra            # Return to the calling function

################
## PART THREE ##
################

testcall:
	# The following registers should not change when called:
	# $ra, $sp, $gp, $s0, $s1, $s2, $s3, $s4, $s5, $s6, $s7 and $fp
	# 12 registers, 4 bytes each, so 48 spaces in necessary in the stack for the calling convention checker
	# Plus four for the inputs - the function, the two arguments and the expected output
	addiu $sp, $sp, -72  # Make stack frame, with space for all the things we need to save
	sw $zero, 68($sp)
	sw $zero, 64($sp)
	sw $a0, 60($sp)
	sw $a1, 56($sp)
	sw $a2, 52($sp)
	sw $a3, 48($sp)
	sw $ra, 44($sp)
	sw $sp, 40($sp)
	sw $gp, 36($sp)
	sw $s0, 32($sp)
	sw $s1, 28($sp)
	sw $s2, 24($sp)
	sw $s3, 20($sp)
	sw $s4, 16($sp)
	sw $s5, 12($sp)
	sw $s6, 8($sp)
	sw $s7, 4($sp)
	sw $fp 0($sp)
	
	### Get parity of stack before calling function ###
	la $a0, ($sp) # Load the start address of the stack as the start address of the function
	li $a1, 14 # Make memparity look at 14 values
        jal memparityarg # Run the memparity 
        sw $v0, 64($sp) # Save the result in the stack, since we'll need to assume the function may not follow the calling conventions.
	
	### Put $a0, $a1, $a2 and $ra back to normal ###
	lw $a0, 60($sp)
	lw $a1, 56($sp)
	lw $a2, 52($sp)
	lw $ra, 44($sp)
	
	### Set up the function arguments ###
	move $t1, $a0 # Put the function memory address in $t1, so that $a0 is freed up for its arguments
	move $a0, $a1 # Move the first argument to $a0
	move $a1, $a2 # Move the second argument to $a1
	
	### Execute! ###
	jalr $t1 # Execute the function
	
	### Cleanup ###
	lw $ra, 44($sp) # Load the original $ra back from the stack
	
	sw $v0, 68($sp) # Put the function's return value in the stack so that memparityarg may be called
	
	### Get parity of stack after calling function ###
	la $a0, ($sp) # Load the start address of the stack as the start address of the function 
	li $a1, 14 
        jal memparityarg # Run the memparity 
        lw $ra, 44($sp) # Load the original $ra back from the stack
        lw $t3, 64($sp) # Get the result of the parity before calling from the stack and save it in $t3
	bne $t3, $v0, testsparneq # If they are not equal, jump to the error message function.
	
	lw $v0, 68($sp) # Put $v0 back to normal
	
	### Check whether calling conventions are followed ###
	
	lw $t2, 48($sp) # Load the expected return value into $t1
	bne $t2, $v0, testreturnneq
	
	lw $t2, 40($sp) # Load the original stack pointer from the stack
	bne $t2, $sp, testspneq
	
	lw $t2, 36($sp) # Load the original general pointer from the stack
	bne $t2, $gp, testgpneq
	
	lw $t2, 32($sp) # Load the original $s0 from the stack
	bne $t2, $s0, tests0neq
	
	lw $t2, 28($sp) # Load the original $s1 from the stack
	bne $t2, $s1, tests1neq
	
	lw $t2, 24($sp) # Load the original $s2 from the stack
	bne $t2, $s2, tests2neq
	
	lw $t2, 20($sp) # Load the original $s3 from the stack
	bne $t2, $s3, tests3neq
	
	lw $t2, 16($sp) # Load the original $s4 from the stack
	bne $t2, $s4, tests4neq
	
	lw $t2, 12($sp) # Load the original $s5 from the stack
	bne $t2, $s5, tests5neq
	
	lw $t2, 8($sp) # Load the original $s6 from the stack
	bne $t2, $s6, tests6neq
	
	lw $t2, 4($sp) # Load the original $s7 from the stack
	bne $t2, $s7, tests7neq
	
	lw $t2, 0($sp) # Load the original function pointer from the stack
	bne $t2, $fp, testfpneq
	
testsexit:
	addiu $sp, $sp, 72  # Put the stack back to normal
	jr $ra
testreturnneq:
        li $v0, 4
        la $a0, errorRVNEQ # Print out a message to say that the return value was not equal!
        syscall
      	j testsexit
testspneq:
        li $v0, 4
        la $a0, errorSPNEQ # Print out a message to say that the stack pointer was not equal!
        syscall
      	j testsexit
testgpneq:
        li $v0, 4
        la $a0, errorGPNEQ # Print out a message to say that the general pointer was not equal!
        syscall
      	j testsexit
tests0neq:
        li $v0, 4
        la $a0, errorS0NEQ # Print out a message to say that $s0 was not equal!
        syscall
      	j testsexit
tests1neq:
        li $v0, 4
        la $a0, errorS1NEQ # Print out a message to say that $s1 was not equal!
        syscall
      	j testsexit
tests2neq:
        li $v0, 4
        la $a0, errorS2NEQ # Print out a message to say that $s2 was not equal!
        syscall
      	j testsexit
tests3neq:
        li $v0, 4
        la $a0, errorS3NEQ # Print out a message to say that $s3 was not equal!
        syscall
      	j testsexit
tests4neq:
        li $v0, 4
        la $a0, errorS4NEQ # Print out a message to say that $s4 was not equal!
        syscall
      	j testsexit
tests5neq:
        li $v0, 4
        la $a0, errorS5NEQ # Print out a message to say that $s5 was not equal!
        syscall
      	j testsexit
tests6neq:
        li $v0, 4
        la $a0, errorS6NEQ # Print out a message to say that $s6 was not equal!
        syscall
      	j testsexit
tests7neq:
        li $v0, 4
        la $a0, errorS7NEQ # Print out a message to say that $s7 was not equal!
        syscall
      	j testsexit
testfpneq:
        li $v0, 4
        la $a0, errorFPNEQ # Print out a message to say that the function pointer was not equal!
        syscall
      	j testsexit

testsparneq:
	li $v0, 4
	la $a0, errorPARNEQ # Print out a message to say that the parity was not equal!
	syscall
	j testsexit


####################
## TEST FUNCTIONS ##
####################

	### Correct ###
testaddfunc:
	add $v0, $a0, $a1
	jr $ra

testxorfunc:
	xor $v0, $a0, $a1
	jr $ra

	### Broken ###
testbrokens0:
	xor $v0, $a0, $a1
	addiu $s0, $s0, 12345
	jr $ra

testbrokensp:
	xor $v0, $a0, $a1
	li $t1, -1000
	sw $t1, 8($sp)
	jr $ra

testbrokengp:
	xor $v0, $a0, $a1
	addi $gp, $gp, -8
	jr $ra
