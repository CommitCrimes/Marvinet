// 1. Folder Tools
folder('Tools') {
    description('Folder for miscellaneous tools.')
}

// 2. Job: clone-repository
freeStyleJob('Tools/clone-repository') {
    description('Clone a Git repository using the provided URL.')

    parameters {
        stringParam('GIT_REPOSITORY_URL', '', 'Git URL of the repository to clone')
    }

    wrappers {
        preBuildCleanup()
    }

    steps {
        shell('git clone $GIT_REPOSITORY_URL')
    }
}

// 3. Job: SEED
freeStyleJob('Tools/SEED') {
    description('Seed job to generate jobs from GitHub repos.')

    parameters {
        stringParam('GITHUB_NAME', '', 'GitHub repository owner/repo_name (e.g.: EpitechIT31000/chocolatine)')
        stringParam('DISPLAY_NAME', '', 'Display name for the job')
    }

    wrappers {
        preBuildCleanup()
    }

    steps {
        dsl {
            external('/var/jenkins_home/dsl/template_job.groovy')
            removeAction('IGNORE')
        }
    }
}
