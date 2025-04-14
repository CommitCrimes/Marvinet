def repo = GITHUB_NAME
def jobName = DISPLAY_NAME
def repoUrl = "https://github.com/${repo}.git"
def projectUrl = "https://github.com/${repo}"

freeStyleJob(jobName) {
    description("Job generated from GitHub repository ${repo}")

    properties {
        githubProjectUrl(projectUrl)
    }

    scm {
        git {
            remote {
                url(repoUrl)
            }
        }
    }

    triggers {
        scm('* * * * *')
        quietPeriod(0)
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
