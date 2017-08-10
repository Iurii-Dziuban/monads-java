# monads-java
Popular functional Monad implementations in Java

[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badge/)    

[![Build Status](https://travis-ci.org/Iurii-Dziuban/monads-java.svg?branch=master)](https://travis-ci.org/Iurii-Dziuban/monads-java)
[![Coverage Status](https://coveralls.io/repos/github/Iurii-Dziuban/monads-java/badge.svg?branch=master)](https://coveralls.io/github/Iurii-Dziuban/monads-java?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/58e33dc626a5bb0038e421ee/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/58e33dc626a5bb0038e421ee)
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/Iurii-Dziuban/monads-java/issues)

## Table of contents:
 * [Static Analysis QA Checks](#checks)
 * [Project parts](#project-parts)

# Checks

Jacoco code coverage, pmd, checkstyle, enforcer, findbugs

# Project parts

1) Either Monad `com.monad.example.either.Either` (`EitherTest`) 
Monad that has either a value or an Exception. Tests include checks on Monad properties.
2) Try Monad `com.monad.example.try_.Try` (`TryTest`)
Monad that can be either a type `Success` or `Failure`, depending on operations
3) ListMonad `com.monad.example.list.ListMonad` (`ListMonadTest`)
Monad that used to construct Monadic Lists