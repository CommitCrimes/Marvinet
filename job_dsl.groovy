folder('Tools') {
    description('Folder for miscellaneous tools.')
}

job('Tools/clone-repository') {
    description('Clones a Git repository.')
    
    properties {
        parametersDefinitionProperty {
            parameterDefinitions {
                stringParameterDefinition {
                    name('GIT_REPOSITORY_URL')
                    defaultValue('')
                    description('Git URL of the repository to clone')
                    trim(true)
                }
            }
        }
    }
    
    wrappers {
        preBuildCleanup()
    }
    
    steps {
        shell('git clone $GIT_REPOSITORY_URL .')
    }
}

job('Tools/SEED') {
    description('Creates a job using DSL.')
    
    properties {
        parametersDefinitionProperty {
            parameterDefinitions {
                stringParameterDefinition {
                    name('GITHUB_NAME')
                    defaultValue('')
                    description('GitHub repository owner/repo_name (e.g.: "EpitechIT31000/chocolatine")')
                    trim(true)
                }
                stringParameterDefinition {
                    name('DISPLAY_NAME')
                    defaultValue('')
                    description('Display name for the job')
                    trim(true)
                }
            }
        }
    }
    
    steps {
        dsl {
            text('''
                job(DISPLAY_NAME) {
                    properties {
                        githubProjectUrl("https://github.com/${GITHUB_NAME}")
                    }
                    
                    triggers {
                        scm('* * * * *')
                    }
                    
                    wrappers {
                        preBuildCleanup()
                    }
                    
                    scm {
                        git {
                            remote {
                                github(GITHUB_NAME)
                            }
                            branches('*/master', '*/main')
                        }
                    }
                    
                    steps {
                        shell('make fclean')
                        shell('make')
                        shell('make tests_run')
                        shell('make clean')
                    }
                }
            ''')
        }
    }
}