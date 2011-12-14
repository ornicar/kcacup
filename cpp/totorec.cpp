

#include <stdlib.h>
#include <string.h>
#include <float.h>
#include <fstream>
#include <map>
#include "totoheader.h"
using namespace std ;

//----------------------------------------------------------------------
void
print_usage(void)
{
	fprintf(stderr,"usage :\ntotorec replay_file_path\n") ;
}
//----------------------------------------------------------------------
int
main(int argc, char **argv)
{
	if(argc!=2)
	{
		print_usage() ;
		exit(WRONG_USAGE) ;
	}

	//
	int execution_code = ERREUR_INCONNUE ;
	ifstream stream ;
	//
	try
	{
		// read file path
		stream.open(argv[1], (ios_base::in|ios_base::binary)) ;
		if(stream.is_open()==false)
		{
			// exits with open error
			exit(FILE_OPEN_ERROR) ;
		}
		else
		{
			// 1. nb of frames (0..3)
			char nb_frames_buff[sizeof_uint] ;
			READ(nb_frames_buff, sizeof_uint) ;
			unsigned int nb_frames ;
			endian_swap(nb_frames_buff, nb_frames) ;
			// (4..7)
			stream.ignore(sizeof_uint) ;
			CHECK_STREAM() ;

			// 2. flag pour les recs multi-players (8..11)
			char multi_flag ;
			stream >> multi_flag ;
			CHECK_STREAM() ;
			//bool rec_is_multi = (multi_flag==1) ;
			stream.ignore(3) ;
			CHECK_STREAM() ;
			stream.ignore(sizeof_uint) ;
			CHECK_STREAM() ;

			// 4. version number (12..15)
			char version_number_buff[sizeof_uint] ;
			READ(version_number_buff, sizeof_uint) ;
			unsigned int version_number ;
			endian_swap(version_number_buff, version_number) ;

			// 5. level file name
			char level_file_name[16] ;
			READ(level_file_name, 16) ;

			// 6. lire tout un tas de bordel qu'on va pas utiliser
			int taille_bordel =
				(nb_frames*sizeof_float) +	// body x absolute positions
				(nb_frames*sizeof_float) +	// body y absolute positions
				(nb_frames*sizeof_short) +	// left wheel relative x positions
				(nb_frames*sizeof_short) +	// left wheel relative y positions
				(nb_frames*sizeof_short) +	// right wheel relative x positions
				(nb_frames*sizeof_short) +	// right wheel relative y positions
				(nb_frames*sizeof_short) +	// head relative x positions
				(nb_frames*sizeof_short) +	// head relative y positions
				(nb_frames*sizeof_short) +	// global body rotation (valeur de 0 à 10000)
				(nb_frames*sizeof_char) +	// left wheel rotation (valeur de 0 à 256)
				(nb_frames*sizeof_char) +	// right wheel rotation (valeur de 0 à 256)
				(nb_frames*sizeof_char) +	// global state
				(nb_frames*sizeof_char) +	// unknown
				(nb_frames*sizeof_char) ;	// unknown 
			//
			stream.ignore(taille_bordel) ;
			CHECK_STREAM() ;

			// 7. nb of events
			char nb_events_buff[sizeof_int] ;
			READ(nb_events_buff, sizeof_int) ;
			int nb_events ;
			endian_swap(nb_events_buff, nb_events) ;

			// 8. events
			char current_time_buff[sizeof_double] ;
			double current_time, max_time=(-1.0*DBL_MAX) ;
			char object_index_buff[sizeof_int] ;
			int object_index ;
			char object_type_buff[sizeof_int] ;
			int object_type ;
			//
			char magic_number_buff[sizeof_double] ;
			double magic_number ;
			long m0 = 0xe01e01e1, m1 = 0x406c9e01 ;
			memcpy(magic_number_buff,&m0,sizeof_long) ;
			memcpy(&magic_number_buff[sizeof_long],&m1,sizeof_long) ;
			memcpy(&magic_number,magic_number_buff, sizeof_double) ;
			//
			std::map<int,double> objectIndex_to_time ;
			// events
			for(int e=0; e<nb_events; ++e)
			{
				READ(current_time_buff, sizeof_double) ;
				READ(object_index_buff, sizeof_int) ;
				READ(object_type_buff, sizeof_int) ;
				//
				endian_swap(current_time_buff, current_time) ;
				endian_swap(object_index_buff, object_index) ;
				endian_swap(object_type_buff, object_type) ;
				//
				if(object_type==0)
				{
					current_time = (current_time*magic_number) ;
					if(current_time>max_time)
					{
						max_time = current_time ;
					}
					//
					if(objectIndex_to_time.count(object_index)==0)
					{
						objectIndex_to_time.insert( std::make_pair<int,double>(object_index, current_time) ) ;
					}
					else
					{
						std::map<int,double>::iterator _where = objectIndex_to_time.find(object_index) ;
						double existing_time = _where->second ;
						_where->second = std::max(existing_time, current_time) ;
					}
				}
			}

			std::multimap<double,int> time_to_objectIndex ;
			std::map<int,double>::const_iterator it ;
			for(it=objectIndex_to_time.begin(); it!=objectIndex_to_time.end(); ++it)
			{
				time_to_objectIndex.insert( std::make_pair<double,int>(it->second,it->first) ) ;
			}


			// version number
			fprintf(stdout,"%u\n", version_number) ;
			// level filename
			fprintf(stdout,"%s\n", level_file_name) ;
			//// multi rec flag
			//if(rec_is_multi)
			//{
			//	fprintf(stdout,"true\n") ;
			//}
			//else
			//{
			//	fprintf(stdout,"false\n") ;
			//}
			// number of object events (apples/flower)
			fprintf(stdout,"%d\n", time_to_objectIndex.size()) ;
			// events
			std::multimap<double,int>::const_iterator it2 ;
			for(it2=time_to_objectIndex.begin(); it2!=time_to_objectIndex.end(); ++it2)
			{
				fprintf(stdout,"%.15f;%d\n", it2->first, it2->second) ;
			}

			//
			stream.close() ;
			//
			execution_code = EXECUTION_OK ;
		}
	}
	catch(exception e)
	{
		fprintf(stderr,"exception: %s\n", e.what()) ;
		//
		stream.close() ;
	}
	
	return execution_code ;
}
