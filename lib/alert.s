.global	alert

.text
alert:
	movl  $4, %eax
	movl  $1, %ebx
	movl  $msg, %ecx
	movl  $len, %edx
	int   $0x80

	movl  $1, %eax
	movl  $0, %ebx
	int   $0x80
.data
msg:
	.ascii  "\nIndex out of bounds! I'm leaving.\n\n"
	len =   . - msg
