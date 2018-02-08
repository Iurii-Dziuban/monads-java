# Monads Java

Popular functional Monad implementations in Java

Inspired by https://www.youtube.com/watch?v=nkUafcNWiQE

[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badge/)    

[![Build Status](https://travis-ci.org/Iurii-Dziuban/monads-java.svg?branch=master)](https://travis-ci.org/Iurii-Dziuban/monads-java)
[![Coverage Status](https://coveralls.io/repos/github/Iurii-Dziuban/monads-java/badge.svg?branch=master)](https://coveralls.io/github/Iurii-Dziuban/monads-java?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/5a11b2930fb24f2a317170a9/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5a11b2930fb24f2a317170a9)
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/Iurii-Dziuban/monads-java/issues)

## Table of contents:
 * [Static Analysis QA Checks](#checks)
 * [Project parts](#project-parts)
 * [Ideas to try](#ideas)
 
# Checks

`Jacoco`/`cobertura` code coverage, `pmd`, `checkstyle`, `enforcer`, `findbugs`

# Project parts

1) Either Monad `com.monad.example.either.Either` (`EitherTest`) 
Monad that has either a value or an Exception. Tests include checks on Monad properties.
2) Try Monad `com.monad.example.try_.Try` (`TryTest`)
Monad that can be either a type `Success` or `Failure`, depending on operations
3) ListMonad Monad `com.monad.example.list.ListMonad` (`ListMonadTest`)
Monad that used to construct Monadic Lists based on ArrayList
4) List Monad `com.monad.example.list.List` (`ListTest`)
Monad based on head and tail recursive style List
5) Optional Monad `com.monad.example.optional.Optional` (`OptionalTest`)
Optional Monad - similar to java Optional implementation: element or null.
6) Promise Monad `com.monad.example.promise.Promise` (`PromiseTest`)
Not thread safe Promise Monad - ability to chain actions.
7) Writer (Log) Monad `com.monad.example.writer.Log` (`LogTest`)
Based on chaining and saving logs in the order we invoke functions.

# Ideas
- State monad 
- Reader monad 
- Stream monad 
- IO Monad
- Monad in Haskel
