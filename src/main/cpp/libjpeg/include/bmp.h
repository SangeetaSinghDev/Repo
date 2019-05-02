//
// Created by Sangeeta on 08-04-2019.
//

#ifndef __BMP_H__
#define __BMP_H__

#include "./turbojpeg.h"

int loadbmp(char *filename, unsigned char **buf, int *w, int *h, int pf,
	int bottomup);

int savebmp(char *filename, unsigned char *buf, int w, int h, int pf,
	int bottomup);

const char *bmpgeterr(void);

#ifdef __cplusplus
}
#endif

#endif