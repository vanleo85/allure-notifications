<#compress>
    <#if reportLink??>*${reportAvailableAtLink}:* ${reportLink} <br><br></#if>
    <#if passed != 0 > ***${totalPassed}:* ${passed} (${passedPercentage} %)** <br><br></#if>
    *${duration}:* ${time} <br><br>
    <#if chartSource??>${chartSource}</#if>
</#compress>