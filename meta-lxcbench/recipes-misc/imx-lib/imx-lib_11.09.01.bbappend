PR = "${INC_PR}.2"

COMPATIBLE_MACHINE = "(mx5)"

do_more_patches() {
    echo "DEBUG: do_more_patches() start"
    patch -p1 << END

--- imx-lib-11.09.01/ipu/mxc_ipu_hl_lib.c.ORIG	2013-05-20 09:03:01.000000000 +0200
+++ imx-lib-11.09.01/ipu/mxc_ipu_hl_lib.c	2013-05-20 09:03:10.000000000 +0200
@@ -41,7 +41,7 @@
 #include <sys/mman.h>
 #include <sys/types.h>
 #include <sys/stat.h>
-#include <linux/videodev.h>
+#include <linux/videodev2.h>
 #include "mxc_ipu_hl_lib.h"
 
 #define DBG_DEBUG		3
--- imx-lib-11.09.01/screenlayer/ScreenLayer.c.ORIG	2013-05-20 09:10:26.000000000 +0200
+++ imx-lib-11.09.01/screenlayer/ScreenLayer.c	2013-05-20 09:10:39.000000000 +0200
@@ -26,7 +26,7 @@
 #include <semaphore.h>
 #include <linux/ipu.h>
 #include <linux/mxcfb.h>
-#include <linux/videodev.h>
+#include <linux/videodev2.h>
 #include <sys/ioctl.h>
 #include <sys/mman.h>
 #include <sys/stat.h>

END
    echo "DEBUG: do_more_patches() end"
}

# do_unpack_append() was being treated as a Python function, so the syntax was wrong.
# By using that function to run a shell function, the problem is solved.
# Patterned this after similar functions, e.g. in the eglibc recipe.
do_unpack_append() {
    bb.build.exec_func('do_more_patches', d)
}

# === EOF ===
