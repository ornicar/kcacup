
#include <stdlib.h>
#include <string.h>
#include <fstream>
#include "totoheader.h"
using namespace std ;

//----------------------------------------------------------------------
void
print_usage(void)
{
	fprintf(stderr,"usage :\ntotolev level_file_path\n") ;
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
			char entete[7] ;
			READ(entete, 7) ;
			entete[5] = '\0' ;
			int diff_entete = strcmp("POT14",entete) ;
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
				READ(level_id_buffer, sizeof_uint) ;
				unsigned int level_id ;
				endian_swap(level_id_buffer, level_id) ;

				// 3.
				// Read level title
				char data_block[119] ;
				READ(data_block, 119) ;
				// copy level internal title
				char level_title[51] ;
				for(i=32; i<83; ++i)
				{
					level_title[i-32] = data_block[i] ;
				}
				level_title[50] = '\0' ;

				// 4. read polygon data
				char nb_poly_buff[sizeof_double] ;
				READ(nb_poly_buff, sizeof_double) ;
				double nb_polysd ;
				endian_swap(nb_poly_buff, nb_polysd) ;
				int nb_polys = (int)nb_polysd ;

				int p ;
				char int_buff[sizeof_int] ;
				char double_buff[sizeof_double] ;
				for(p=0; p<nb_polys; ++p)
				{
					READ(int_buff, sizeof_int) ;	// poly property (grass or not)
					READ(int_buff, sizeof_int) ;	// poly num of vertices
					int nb_vertices ;
					endian_swap(int_buff, nb_vertices) ;

					int v ;
					for(v=0; v<nb_vertices; ++v)
					{
						READ(double_buff, sizeof_double) ;	// vertex X
						READ(double_buff, sizeof_double) ;	// vertex Y
					}
				}

				// 5. read objects
				char nb_objects_buff[sizeof_double] ;
				READ(nb_objects_buff, sizeof_double) ;
				double nb_objectsd ;
				endian_swap(nb_objects_buff, nb_objectsd) ;
				int nb_objects = (int)nb_objectsd ;
				int o, object_type ;
				int nb_apples = 0 ;
				for(o=0; o<nb_objects; ++o)
				{
					READ(double_buff, sizeof_double) ;	// object X
					READ(double_buff, sizeof_double) ;	// object Y
					READ(int_buff, sizeof_int) ;		// object property (grass or not)
					endian_swap(int_buff, object_type) ;
					READ(int_buff, sizeof_int) ;		// gravity type if apple
					READ(int_buff, sizeof_int) ;		// animation frame

					if(object_type==2) // APPLE
					{
						++nb_apples ;
					}
				}

				// ok
				execution_code = EXECUTION_OK ;

				fprintf(stdout,"%u\n",level_id) ;
				fprintf(stdout,"%s\n",level_title) ;
				fprintf(stdout,"%d\n",nb_apples) ;
			}
		}
	}
	catch(exception e)
	{
		fprintf(stderr,"exception: %s\n", e.what()) ;
	}
	
	return execution_code ;
}

