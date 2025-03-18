# ~/.bashrc: executed by bash(1) for non-login shells.

umask 022

export LS_OPTIONS='--color=auto'
alias ls='ls $LS_OPTIONS'
alias ll='ls $LS_OPTIONS -l'
alias l='ls $LS_OPTIONS -lA'

# EdgeFirst Client Login & Logout Aliases
alias efc-login='export DVE_TOKEN=\$(edgefirst-client login)'
alias efc-logout='unset DVE_TOKEN'

# Some more alias to avoid making mistakes:
# alias rm='rm -i'
# alias cp='cp -i'
# alias mv='mv -i'