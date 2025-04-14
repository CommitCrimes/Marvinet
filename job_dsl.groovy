// Fichier job_dsl.groovy

// 1. Dossier "Tools"
folder('Tools') {
    description('Folder for miscellaneous tools.')
}

// 2. Job "clone-repository"
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

// 3. Job "SEED"
freeStyleJob('Tools/SEED') {
    description('Seed job to generate a new job from GitHub.')

    parameters {
        stringParam('GITHUB_NAME', '', 'GitHub repository owner/repo_name (e.g.: EpitechIT31000/chocolatine)')
        stringParam('DISPLAY_NAME', '', 'Display name for the job')
    }

    wrappers {
        preBuildCleanup()
    }

    steps {
        dsl {
            text("""
freeStyleJob("\${DISPLAY_NAME}") {
    description("Job generated from GitHub repository \${GITHUB_NAME}")
    
    properties {
        githubProjectUrl("https://github.com/\${GITHUB_NAME}")
    }

    scm {
        git("https://github.com/\${GITHUB_NAME}.git")
    }

    triggers {
        scm('H/1 * * * *')
    }

    wrappers {
        preBuildCleanup()
    }

    steps {
        shell('make fclean')
        shell('make')
        shell('make tests_run')
        shell('make clean')
    }
}
            """)
        }
    }
}
