# ~/.bashrc: executed by bash(1) for non-login shells.

umask 022

export LS_OPTIONS='--color=auto'
alias ls='ls $LS_OPTIONS'
alias ll='ls $LS_OPTIONS -l'
alias l='ls $LS_OPTIONS -lA'

# EdgeFirst Client for Deep View Enterprise Login & Logout Aliases
alias dve-login="export DVE_TOKEN=\$(edgefirst-client token)"
alias dve-logout="unset DVE_TOKEN"

# Some more alias to avoid making mistakes:
# alias rm='rm -i'
# alias cp='cp -i'
# alias mv='mv -i'
