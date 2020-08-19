
#ifndef LORAD_COMMON_H
#define LORAD_COMMON_H

#include <sys/ioctl.h>

#define BUF_SIZE        256
#define BUF_M_SIZE      160
#define BUF_TINY_SIZE   20

#ifndef TRUE
#define TRUE 1
#endif

#ifndef FALSE
#define FALSE 0
#endif


#define ETHER_MSG   1
#define ETHER_LOC   2
#define ETHER_CMD   3
#define ETHER_ALV   4


#define SHM_MSG_NONE        0
#define SHM_MSG_FOR_OUT     1
#define SHM_MSG_FOR_IN      2

#define SHM_DATA_FILE   "/data/user/0/com.offgrid.coupler/files/shm.data"
#define SHM_LOCK_FILE   "/data/user/0/com.offgrid.coupler/files/shm.lock"


typedef struct {
    char uid[BUF_TINY_SIZE];
    int type;
    char data[BUF_M_SIZE];
} ether_msg_t;

typedef struct {
    int cmd;
    ether_msg_t msg;
} shm_data_t;

typedef struct {
    int shm_fd;
    int lock_fd;
    shm_data_t *map;
} shm_info_t;


#endif //LORAD_COMMON_H
