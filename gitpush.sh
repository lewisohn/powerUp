ssh-agent
eval "$(ssh-agent)"
ssh-add ../.ssh/id_rsa
git add --all .
git commit -m "Update"
git push
