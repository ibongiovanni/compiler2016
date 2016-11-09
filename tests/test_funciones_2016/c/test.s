	.file	"test.c"
	.text
	.globl	factorial
	.type	factorial, @function
factorial:
.LFB0:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movl	%edi, -20(%rbp)
	movl	$15, -4(%rbp)
	movl	-20(%rbp), %eax
	cmpl	-4(%rbp), %eax
	jle	.L2
	movl	$-1, %eax
	jmp	.L3
.L2:
	movl	$0, -12(%rbp)
	movl	$1, -8(%rbp)
	jmp	.L4
.L5:
	addl	$1, -12(%rbp)
	movl	-8(%rbp), %eax
	imull	-12(%rbp), %eax
	movl	%eax, -8(%rbp)
.L4:
	movl	-12(%rbp), %eax
	cmpl	-20(%rbp), %eax
	jl	.L5
	movl	-8(%rbp), %eax
.L3:
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	factorial, .-factorial
	.globl	factorialFor
	.type	factorialFor, @function
factorialFor:
.LFB1:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movl	%edi, -20(%rbp)
	movl	$15, -4(%rbp)
	movl	-20(%rbp), %eax
	cmpl	-4(%rbp), %eax
	jle	.L7
	movl	$-1, %eax
	jmp	.L8
.L7:
	movl	$0, -12(%rbp)
	movl	$1, -8(%rbp)
	jmp	.L9
.L10:
	addl	$1, -12(%rbp)
	movl	-8(%rbp), %eax
	imull	-12(%rbp), %eax
	movl	%eax, -8(%rbp)
.L9:
	movl	-12(%rbp), %eax
	cmpl	-20(%rbp), %eax
	jl	.L10
	movl	-8(%rbp), %eax
.L8:
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1:
	.size	factorialFor, .-factorialFor
	.globl	factorialArray
	.type	factorialArray, @function
factorialArray:
.LFB2:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movl	%edi, -84(%rbp)
	movl	$15, -68(%rbp)
	movl	$0, -80(%rbp)
	jmp	.L12
.L15:
	movl	$0, -76(%rbp)
	movl	$1, -72(%rbp)
	jmp	.L13
.L14:
	addl	$1, -76(%rbp)
	movl	-72(%rbp), %eax
	imull	-76(%rbp), %eax
	movl	%eax, -72(%rbp)
.L13:
	movl	-76(%rbp), %eax
	cmpl	-80(%rbp), %eax
	jl	.L14
	movl	-80(%rbp), %eax
	cltq
	movl	-72(%rbp), %edx
	movl	%edx, -64(%rbp,%rax,4)
	addl	$1, -80(%rbp)
.L12:
	movl	-80(%rbp), %eax
	cmpl	-68(%rbp), %eax
	jl	.L15
	movl	-68(%rbp), %eax
	subl	$1, %eax
	cmpl	-84(%rbp), %eax
	jge	.L16
	movl	$-1, %eax
	jmp	.L18
.L16:
	movl	-84(%rbp), %eax
	cltq
	movl	-64(%rbp,%rax,4), %eax
.L18:
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE2:
	.size	factorialArray, .-factorialArray
	.globl	nthprime
	.type	nthprime, @function
nthprime:
.LFB3:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movl	%edi, -20(%rbp)
	movl	$0, -12(%rbp)
	movl	$2, -8(%rbp)
	addl	$1, -20(%rbp)
	jmp	.L20
.L25:
	movl	$0, -4(%rbp)
	addl	$1, -12(%rbp)
	jmp	.L21
.L24:
	movl	-12(%rbp), %eax
	cltd
	idivl	-8(%rbp)
	movl	%edx, %eax
	testl	%eax, %eax
	jne	.L22
	movl	$1, -4(%rbp)
	jmp	.L21
.L22:
	addl	$1, -8(%rbp)
.L21:
	cmpl	$0, -4(%rbp)
	jne	.L23
	movl	-8(%rbp), %eax
	cmpl	-12(%rbp), %eax
	jl	.L24
.L23:
	movl	$2, -8(%rbp)
	cmpl	$0, -4(%rbp)
	jne	.L20
	subl	$1, -20(%rbp)
.L20:
	cmpl	$0, -20(%rbp)
	jg	.L25
	movl	-12(%rbp), %eax
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE3:
	.size	nthprime, .-nthprime
	.globl	nthprimeArray
	.type	nthprimeArray, @function
nthprimeArray:
.LFB4:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$424, %rsp
	movl	%edi, -420(%rbp)
	movl	$0, -404(%rbp)
	jmp	.L28
.L29:
	movl	-404(%rbp), %eax
	movl	%eax, %edi
	call	nthprime
	movl	-404(%rbp), %edx
	movslq	%edx, %rdx
	movl	%eax, -400(%rbp,%rdx,4)
	addl	$1, -404(%rbp)
.L28:
	cmpl	$99, -404(%rbp)
	jle	.L29
	movl	-420(%rbp), %eax
	subl	$1, %eax
	cltq
	movl	-400(%rbp,%rax,4), %eax
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE4:
	.size	nthprimeArray, .-nthprimeArray
	.globl	int2bin
	.type	int2bin, @function
int2bin:
.LFB5:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movl	%edi, -36(%rbp)
	movl	$0, -20(%rbp)
	movl	$0, -12(%rbp)
.L35:
	cmpl	$1, -36(%rbp)
	jle	.L32
	movl	-36(%rbp), %eax
	cltd
	shrl	$31, %edx
	addl	%edx, %eax
	andl	$1, %eax
	subl	%edx, %eax
	movl	%eax, -16(%rbp)
	movl	$0, -8(%rbp)
.L34:
	movl	-8(%rbp), %eax
	cmpl	-12(%rbp), %eax
	jge	.L33
	movl	-16(%rbp), %edx
	movl	%edx, %eax
	sall	$2, %eax
	addl	%edx, %eax
	addl	%eax, %eax
	movl	%eax, -16(%rbp)
	addl	$1, -8(%rbp)
	jmp	.L34
.L33:
	nop
	movl	-16(%rbp), %eax
	addl	%eax, -20(%rbp)
	addl	$1, -12(%rbp)
	movl	-36(%rbp), %eax
	movl	%eax, %edx
	shrl	$31, %edx
	addl	%edx, %eax
	sarl	%eax
	movl	%eax, -36(%rbp)
	jmp	.L35
.L32:
	nop
	movl	$0, -4(%rbp)
.L37:
	movl	-4(%rbp), %eax
	cmpl	-12(%rbp), %eax
	jge	.L36
	movl	-36(%rbp), %edx
	movl	%edx, %eax
	sall	$2, %eax
	addl	%edx, %eax
	addl	%eax, %eax
	movl	%eax, -36(%rbp)
	addl	$1, -4(%rbp)
	jmp	.L37
.L36:
	nop
	movl	-36(%rbp), %eax
	movl	-20(%rbp), %edx
	addl	%edx, %eax
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE5:
	.size	int2bin, .-int2bin
	.globl	gcd
	.type	gcd, @function
gcd:
.LFB6:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$32, %rsp
	movl	%edi, -20(%rbp)
	movl	%esi, -24(%rbp)
	movl	$1, -8(%rbp)
	movl	-8(%rbp), %eax
	movl	%eax, -4(%rbp)
	movl	-20(%rbp), %eax
	movl	%eax, %edi
	call	print_int
	movl	-24(%rbp), %eax
	movl	%eax, %edi
	call	print_int
	jmp	.L40
.L42:
	movl	-20(%rbp), %eax
	cltd
	idivl	-8(%rbp)
	movl	%edx, %eax
	testl	%eax, %eax
	jne	.L41
	movl	-24(%rbp), %eax
	cltd
	idivl	-8(%rbp)
	movl	%edx, %eax
	testl	%eax, %eax
	jne	.L41
	movl	-8(%rbp), %eax
	movl	%eax, -4(%rbp)
.L41:
	addl	$1, -8(%rbp)
.L40:
	movl	-24(%rbp), %eax
	movl	-20(%rbp), %edx
	addl	%edx, %eax
	cmpl	-8(%rbp), %eax
	jg	.L42
	movl	-4(%rbp), %eax
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE6:
	.size	gcd, .-gcd
	.globl	potencia
	.type	potencia, @function
potencia:
.LFB7:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movl	%edi, -20(%rbp)
	movl	%esi, -24(%rbp)
	movl	$1, -4(%rbp)
	movl	$1, -8(%rbp)
.L47:
	movl	-8(%rbp), %eax
	cmpl	-24(%rbp), %eax
	jl	.L45
	movl	-8(%rbp), %eax
	cmpl	-24(%rbp), %eax
	jne	.L46
.L45:
	movl	-4(%rbp), %eax
	imull	-20(%rbp), %eax
	movl	%eax, -4(%rbp)
	addl	$1, -8(%rbp)
	jmp	.L47
.L46:
	movl	-4(%rbp), %eax
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE7:
	.size	potencia, .-potencia
	.globl	test
	.type	test, @function
test:
.LFB8:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	pushq	%rbx
	subq	$24, %rsp
	.cfi_offset 3, -24
	movl	$2, -20(%rbp)
	movl	$4, %edi
	call	factorial
	movl	%eax, %ebx
	movl	$3, %edi
	call	factorial
	movl	%ebx, %esi
	movl	%eax, %edi
	call	gcd
	movl	%eax, %edi
	call	print_int
	movl	$4, %edi
	call	factorial
	movl	%eax, %ebx
	movl	$3, %edi
	call	factorial
	movl	%ebx, %esi
	movl	%eax, %edi
	call	gcd
	movl	%eax, %edi
	call	nthprimeArray
	movl	%eax, %edi
	call	print_int
	movl	$4, %edi
	call	factorial
	movl	%eax, %ebx
	movl	$3, %edi
	call	factorial
	movl	%ebx, %esi
	movl	%eax, %edi
	call	gcd
	movl	%eax, %edi
	call	nthprimeArray
	movl	%eax, %edx
	movl	-20(%rbp), %eax
	movl	%edx, %esi
	movl	%eax, %edi
	call	potencia
	movl	%eax, -20(%rbp)
	movl	-20(%rbp), %eax
	movl	%eax, %edi
	call	print_int
	addq	$24, %rsp
	popq	%rbx
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE8:
	.size	test, .-test
	.globl	test1
	.type	test1, @function
test1:
.LFB9:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$16, %rsp
	movl	$2, -4(%rbp)
	movl	$0, %eax
	call	test
	movl	-4(%rbp), %eax
	movl	%eax, %edi
	call	print_int
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE9:
	.size	test1, .-test1
	.globl	main
	.type	main, @function
main:
.LFB10:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	pushq	%rbx
	subq	$40, %rsp
	.cfi_offset 3, -24
	movl	$1, %edi
	movl	$0, %eax
	call	init_input
	movl	$2, %edi
	call	print_string
	movl	$0, %eax
	call	get_int
	movl	%eax, -44(%rbp)
	movl	$0, -48(%rbp)
	jmp	.L52
.L53:
	movl	$0, %eax
	call	get_int
	movl	%eax, -40(%rbp)
	movl	-40(%rbp), %eax
	movl	%eax, %edi
	call	factorial
	movl	%eax, -40(%rbp)
	movl	-40(%rbp), %eax
	movl	%eax, %edi
	call	print_int
	addl	$1, -48(%rbp)
.L52:
	movl	-48(%rbp), %eax
	cmpl	-44(%rbp), %eax
	jl	.L53
	movl	$1, %edi
	call	print_string
	movl	$4, %edi
	call	print_string
	movl	$0, %eax
	call	get_int
	movl	%eax, -44(%rbp)
	movl	$0, -48(%rbp)
	jmp	.L54
.L55:
	movl	$0, %eax
	call	get_int
	movl	%eax, -36(%rbp)
	movl	-36(%rbp), %eax
	movl	%eax, %edi
	call	factorialArray
	movl	%eax, -36(%rbp)
	movl	-36(%rbp), %eax
	movl	%eax, %edi
	call	print_int
	addl	$1, -48(%rbp)
.L54:
	movl	-48(%rbp), %eax
	cmpl	-44(%rbp), %eax
	jl	.L55
	movl	$1, %edi
	call	print_string
	movl	$5, %edi
	call	print_string
	movl	$0, %eax
	call	get_int
	movl	%eax, -44(%rbp)
	movl	$0, -48(%rbp)
	jmp	.L56
.L57:
	movl	$0, %eax
	call	get_int
	movl	%eax, -32(%rbp)
	movl	-32(%rbp), %eax
	movl	%eax, %edi
	call	nthprime
	movl	%eax, -32(%rbp)
	movl	-32(%rbp), %eax
	movl	%eax, %edi
	call	print_int
	addl	$1, -48(%rbp)
.L56:
	movl	-48(%rbp), %eax
	cmpl	-44(%rbp), %eax
	jl	.L57
	movl	$1, %edi
	call	print_string
	movl	$6, %edi
	call	print_string
	movl	$0, %eax
	call	get_int
	movl	%eax, -44(%rbp)
	movl	$0, -48(%rbp)
	jmp	.L58
.L59:
	movl	$0, %eax
	call	get_int
	movl	%eax, -28(%rbp)
	movl	-28(%rbp), %eax
	movl	%eax, %edi
	call	nthprimeArray
	movl	%eax, -28(%rbp)
	movl	-28(%rbp), %eax
	movl	%eax, %edi
	call	print_int
	addl	$1, -48(%rbp)
.L58:
	movl	-48(%rbp), %eax
	cmpl	-44(%rbp), %eax
	jl	.L59
	movl	$1, %edi
	call	print_string
	movl	$7, %edi
	call	print_string
	movl	$0, %eax
	call	get_int
	movl	%eax, -44(%rbp)
	movl	$0, -48(%rbp)
	jmp	.L60
.L61:
	movl	$0, %eax
	call	get_int
	movl	%eax, -24(%rbp)
	movl	-24(%rbp), %eax
	movl	%eax, %edi
	call	int2bin
	movl	%eax, -24(%rbp)
	movl	-24(%rbp), %eax
	movl	%eax, %edi
	call	print_int
	addl	$1, -48(%rbp)
.L60:
	movl	-48(%rbp), %eax
	cmpl	-44(%rbp), %eax
	jl	.L61
	movl	$1, %edi
	call	print_string
	movl	$8, %edi
	call	print_string
	movl	$0, %eax
	call	get_int
	movl	%eax, -44(%rbp)
	movl	$0, -48(%rbp)
	jmp	.L62
.L63:
	movl	$0, %eax
	call	get_int
	movl	%eax, %ebx
	movl	$0, %eax
	call	get_int
	movl	%ebx, %esi
	movl	%eax, %edi
	call	gcd
	movl	%eax, -20(%rbp)
	movl	-20(%rbp), %eax
	movl	%eax, %edi
	call	print_int
	addl	$1, -48(%rbp)
.L62:
	movl	-48(%rbp), %eax
	cmpl	-44(%rbp), %eax
	jl	.L63
	movl	$1, %edi
	call	print_string
	movl	$9, %edi
	call	print_string
	movl	$0, %eax
	call	test
	movl	$1, %edi
	call	print_string
	movl	$10, %edi
	call	print_string
	movl	$0, %eax
	call	test1
	movl	$1, %edi
	call	print_string
	movl	$0, %eax
	call	close_input
	movl	$1, %eax
	addq	$40, %rsp
	popq	%rbx
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE10:
	.size	main, .-main
	.ident	"GCC: (Ubuntu 4.8.4-2ubuntu1~14.04.3) 4.8.4"
	.section	.note.GNU-stack,"",@progbits
