// Copyright 2021 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.engine.shared.core.identity.credential;

import org.eclipse.collections.impl.utility.LazyIterate;
import org.finos.legend.engine.shared.core.identity.Identity;

import javax.security.auth.Subject;
import java.security.PrivilegedActionException;
import java.util.Objects;

public class KerberosUtils
{

    public static <T> T doAs(final Identity identity, final java.security.PrivilegedAction<T> action)
    {
        LegendKerberosCredential wrapper = identity.getCredential(LegendKerberosCredential.class).get();
        return Subject.doAs(wrapper.getSubject(), action);
    }

    public static <T> T doAs(final Identity identity, final java.security.PrivilegedExceptionAction<T> action) throws PrivilegedActionException
    {
        LegendKerberosCredential wrapper = identity.getCredential(LegendKerberosCredential.class).get();
        return Subject.doAs(wrapper.getSubject(), action);
    }

    public static Subject getSubjectFromIdentity(Identity identity)
    {
        return LazyIterate.selectInstancesOf(identity.getCredentials(), LegendKerberosCredential.class)
                .select(Objects::nonNull)
                .collect(LegendKerberosCredential::getSubject)
                .getFirst();
    }
}