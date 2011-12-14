
#ifndef _TOTO_HEADER_H_
#define _TOTO_HEADER_H_
//----------------------------------------------------------------------
// en dur
#define sizeof_char		(1)
#define sizeof_short	(2)
#define sizeof_int		(4)
#define sizeof_uint		(4)
#define sizeof_long		(4)
#define sizeof_float	(4)
#define sizeof_double	(8)
//----------------------------------------------------------------------
enum ExecutionCode
{
	WRONG_USAGE			= -1,
	EXECUTION_OK		=  0,
	FILE_OPEN_ERROR		=  1,
	BAD_TYPE_CONVERSION	=  2,
	BAD_HEADER			=  3,
	ERREUR_INCONNUE		=  4,
	UNEXPECTED_EOF		=  5
} ;
//----------------------------------------------------------------------
#define CHECK_STREAM()\
		if(stream.good()==false)\
		{\
			stream.close() ;\
			exit(UNEXPECTED_EOF) ;\
		}

//----------------------------------------------------------------------
#define READ(var_buff, var_buff_size)\
		do{\
			stream.read(var_buff,var_buff_size) ;\
			CHECK_STREAM() ;\
		}while(0)

//----------------------------------------------------------------------
template<typename T>
void
endian_swap(char *membuf, T & variable)
{
	int size_type = sizeof(T) ;

	// assert bourrin : que des swap de mémoire de taille paire (2, 4, 8)
	if((size_type%2)!=0)
	{
		exit(BAD_TYPE_CONVERSION) ;
	}

	// swap
	int k, l ;
	for(k=0; k<size_type; ++k)
	{
		l = (size_type-k-1) ;
		char tmp = membuf[k] ;
		membuf[k] = membuf[l] ;
		membuf[l] = tmp ;
	}
	// copie mémoire
	memcpy(&variable, membuf, size_type) ;
}

#endif // _TOTO_HEADER_H_
