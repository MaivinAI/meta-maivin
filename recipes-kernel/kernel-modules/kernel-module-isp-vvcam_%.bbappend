KERNEL_MODULE_AUTOLOAD += " vvcam-video "

# Au-Zone's fork, which carries a number of improvements to the ISP kernel
# driver. See github.com/EdgeFirstAI/isp-vvcam (branch edgefirst) for details.
ISP_KERNEL_SRC = "git://github.com/EdgeFirstAI/isp-vvcam.git;protocol=https"
SRCBRANCH = "edgefirst"
SRCREV = "95f298e14c9e6447187761945661766451a3a641"
