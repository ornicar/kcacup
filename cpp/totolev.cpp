
#include <stdlib.h>
#include <string.h>
#include <fstream>
using namespace std ;

//----------------------------------------------------------------------
enum ExecutionCode
{
	WRONG_USAGE			= -1,
	EXECUTION_OK		=  0,
	FILE_OPEN_ERROR		=  1,
	BAD_HEADER			=  2,
	BAD_TYPE_CONVERSION	=  3,
	ERREUR_INCONNUE		=  4
} ;
//----------------------------------------------------------------------
// en dur
const int sizeof_char	= (1) ;
const int sizeof_int	= (4) ;
const int sizeof_uint	= (4) ;
const int sizeof_float	= (4) ;
const int sizeof_double	= (8) ;
//----------------------------------------------------------------------
void
print_usage(void)
{
	fprintf(stderr,"usage :\ntotolev level_file_path\n") ;
}
//----------------------------------------------------------------------
template<typename T>
T
endian_swap(char *membuf)
{
	T result ;
	int size_type = sizeof(T) ;

	// assert bourrin : que des swap de m?moire de taille paire (2, 4, 8)
	if((size_type%2)!=0)
	{
		exit(BAD_TYPE_CONVERSION) ;
	}

	// swap
	int i, j ;
	for(i=0; i<size_type; ++i)
	{
		j = (size_type-i-1) ;
		char tmp = membuf[i] ;
		membuf[i] = membuf[j] ;
		membuf[j] = tmp ;
	}
	// copie m?moire
	memcpy(&result,membuf,size_type) ;

	//
	return result ;
}
//----------------------------------------------------------------------
int
main(int argc, char **argv)
{
	if(argc!=2)
	{
		print_usage() ;
		return WRONG_USAGE ;
	}

	// init optimiste :
	int i, execution_code = ERREUR_INCONNUE ;
	//
	try
	{
		// read file path
		ifstream stream ;
		stream.open(argv[1], (ios_base::in|ios_base::binary)) ;
		if(stream.fail())
		{
			// exits with open error
			exit(FILE_OPEN_ERROR) ;
		}
		else
		{
			// 1.
			// Read file header :
			char entete[7], pot14[6] ;
			for(i=0; i<7; i++)
			{
				stream >> entete[i] ;
				if(i<5)
					pot14[i] = entete[i] ;
			}
			pot14[5] = '\0' ;
			int diff_entete = strcmp("POT14",pot14) ;
			if(diff_entete!=0)
			{
				// exits with bad header code
				exit(BAD_HEADER) ;
			}
			else
			{
				// 2.
				// Read level ID
				char level_id_buffer[sizeof_uint] ;
				for(i=0; i<sizeof_uint; i++)
				{
					stream >> level_id_buffer[i] ;
				}
				unsigned int level_id = endian_swap<unsigned int>(level_id_buffer) ;

				// 3.
				// Read level title
				char data_block[119], level_title[51] ;
				for(i=0; i<119; ++i)
				{
					stream >> data_block[i] ;
					if(i>31 && i<83)
					{
						level_title[i-32] = data_block[i] ;
					}
				}
				level_title[50] = '\0' ;

				// ok
				execution_code = EXECUTION_OK ;

				fprintf(stdout,"%u\n",level_id) ;
				fprintf(stdout,"%s\n",level_title) ;
			}
		}
	}
	catch(exception e)
	{
		fprintf(stderr,"exception: %s\n", e.what()) ;
	}

	return execution_code ;
}

