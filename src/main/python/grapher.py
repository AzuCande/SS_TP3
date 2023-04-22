import matplotlib
import matplotlib.pyplot as plt
import pandas as pd
import scipy

# x = [1,2,3,4,5]
# y = [340.6,272.6,246,235,229.2]
# errors = [74.42311469,39.62070166,40.54626987,20.29778313,18.8600106]
#
# plt.xlabel("Velocidad Inicial Vx (m/s)")
# plt.ylabel("Tiempo (ms)")
#
# plt.errorbar(x, y, errors, fmt='o', color='black', ecolor='lightgray', elinewidth=3, capsize=0, linestyle='solid', linewidth=1, markersize=4)

x = [0.531551473454602,0.000301472014381021,0.00013512323280711,0.000248857630086973,0.000076500564415016,0.000148266110841732,0.00029271486307405,0.000331716114588931,0.000336524120879611,0.00087929785998244,0.000419315065919455,0.000724133364912192,0.00161392853448221,0.00099289341616422,0.000909817034354707,0.001757081855176,0.00269495216273461,0.00417968076689039,0.00531393893036496,0.0129257475967427,0.0124236817233383,0.042556361505906,0.152414206998378,0.0915698208712125,0.0751622449997723,0.108276474097514,0.158657161280318,0.152302940311004,0.189495954494298,0.133597948339302,0.104876649646469,0.174889906208973,0.287449013039845,0.218935806182324,0.297246871529832,0.447837847713404,0.206468845892572,0.287718534778399,0.134732616044997,0.522131811789937,0.161810051550893,0.426824692856262,0.258869989055673,0.158539089945467,0.19431232572059,0.108826499998639,0.136754385755276,0.157348881184652,0.159738528063208,0.278481135464463,0.291680719791994,0.459964894499544,0.211907391298067,0.0859341520269475,0.521235633382679,0.434822551944832,0.28772439523533,0.274054231579784,0.274734121869041,0.191030292545481,0.238683333912506,0.132469913027243,0.463464046970961,0.172137863160908,0.123270345031611,0.324334579956322,0.288854825074715,0.165013024808568,0.176288444235709,0.363150612248673,0.174476912272071,0.391120729406578,0.0842716223595346,0.292829487782123,0.310508852013466,0.107789577745444,0.0983124698737501,0.208590470342578,0.544463174186808,0.149004764547468,0.236468016194134,0.36168737853677,0.504719949946765,0.166816367789621,0.221116671218416,0.206578268500368,0.24921920269127,0.262962447136049,0.472410704583758,0.325299016611471,0.235648826614611,0.233772475043065,0.214289910255996,0.158164898481798,0.177705621033881,0.167045075702448,0.569870194606622,0.589413224883984,0.304796787390496,0.25009429593012,0.340059624883901,0.462164197163904,0.279857147920544,0.429350384935948,0.895795304958046,0.191964953673367,0.368404097166781,0.628217404254278,0.394827871785839,0.364803885207385,0.538141049928518,0.169343179563429,0.580545979633354,0.46711681014559,0.163560144857711,0.534426547997986,0.528760627026503,0.486990087140111,0.292201271849891,0.660459001113781,0.453398025666287,0.202896703545092,0.217350963207192,0.342903809001293,0.771976457514876,0.576068131005085,0.808495301360193,0.655677852259185,0.767263485875231,0.595839592194857,0.641688487099956,0.464475630140293,0.569223078202759,0.595831955992582,0.516352507406002,0.763979292721492,0.510210053268983,0.303944200188125,0.820877546087851,0.216027463226279,0.198723201495459,0.56107703222673,0.494043278869099,0.402658650303856,0.321117281747507,0.674887902276136,0.707309639553744,0.742318447908644,0.775993821324187,0.604006964035187,0.822565967417235,0.477360952926397,0.512129769979232,0.290397792369562,0.502760787645184,0.839945457679999,0.545871746492669,0.774929374349371,1.84203166033115,1.0384877899673,0.825239142701013,0.61890788765657,0.462208431137477,1.16442879595626,0.706916462513395,0.940238024755274,0.389277564403602,0.3119281778563,0.276591708893258,1.0875728734111,1.34507683830082,0.749990360462551,0.559305528931222,1.89425241610128,1.32772896816019,0.489759856767384,1.2998118535141,1.23572304165394,1.33342457419191,0.896180459138012,1.3640763124413,1.44013442208669,2.86914572209971,2.42780775466745,0.350829119964178]

df = pd.DataFrame(x, columns = ['time between events'])
df.plot.kde(bw_method=0.5, title="PDF using Kernel Density Estimation - Bandwidth value=0.3",logx=True);
df.plot.kde(bw_method=0.1, title="PDF using Kernel Density Estimation - Bandwidth value=0.3",logx=True);
df.plot.kde(bw_method=0.01, title="PDF using Kernel Density Estimation - Bandwidth value=0.3", logx=True);
df.plot.kde(bw_method=0.001, title="PDF using Kernel Density Estimation - Bandwidth value=0.3", logx=True);
plt.show()

# plt.hist(x, 10, density = False,
#          histtype ='bar')
#
# plt.xlabel("Tiempo medio entre eventos (s)")
# plt.ylabel("Frecuencia media")
# plt.show()



